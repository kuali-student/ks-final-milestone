class CourseAdapter

  # course_adapter can be extended and set_course_data and course_error_message can be overridden to create a new course adapter for a different course source

  def set_course_data(sisCourse)
    @code = sisCourse.crs_code
    @short_name = sisCourse.crs_shorttitle
    @long_name = sisCourse.crs_longtitle
    @division = sisCourse.crs_dpt_cd
    @suffix_code = sisCourse.crs_no
    @descr = sisCourse.crs_description
    @level = level(sisCourse)
    @primary_admin_org = "1013"
    @effective_date = effective_date(sisCourse)
    @expected_first_atp = expected_first_atp(sisCourse)
    @atp_duration_type_key = "kuali.atp.duration.Week"
    @time_quantity = std_duration_time_quantity(sisCourse)
    @expiration_date = expiration_date(sisCourse)
    @is_enrollable = false
    @has_early_drop_dead_line = false
    @default_enrollment_estimate = 0
    @default_maximum_enrollment = 0
    @is_hazardous_for_disabled_students = 0
    @is_hazardous_for_disabled_students = false
    @clu_info_type = "kuali.lu.type.CreditCourse"
    @clu_info_state = "activated"
  end


  def create_course(sisCourse)

    set_course_data(sisCourse)

    client = Savon::Client.new LU_SERVICE
    client.create_clu do |soap|

      soap.body = {
        :luTypeKey => "kuali.lu.type.CreditCourse",
        :cluInfo => {
          :officialIdentifier => {
            :code => @code,
            :shortName => @short_name,
            :longName => @long_name,
            :level => @level,
            :division => @division,
            :suffixCode => @suffix_code
            },
          :descr => {
            :plain => @descr
          },
          :primaryAdminOrg => {
            :orgId => @primary_admin_org
          },
          :effectiveDate => @effective_date,
          :expectedFirstAtp => @expected_first_atp,
          :stdDuration => {
            :atpDurationTypeKey => @atp_duration_type_key,
            :timeQuantity => @time_quantity
          },
          :expirationDate => @expiration_date,
          :isEnrollable => @is_enrollable,
          :hasEarlyDropDeadline => @has_early_drop_dead_line,
          :defaultEnrollmentEstimate => @default_enrollment_estimate,
          :defaultMaximumEnrollment => @is_hazardous_for_disabled_students,
          :isHazardousForDisabledStudents => @is_hazardous_for_disabled_students
        },
        :attributes! => {
                :cluInfo => {
                        :type => @clu_info_type,
                        :state => @clu_info_state
                }
        }
      }
      
      if block_given? then
        yield(soap)
      end
    end
  end


  # very crude logic, determines effective date based on start term, needs refinement,
  # maybe should be based on senate approval date 
  def effective_date sisCourse
    year = year_from_term(sisCourse.crs_startterm).to_i
    Date.new(year, 1, 1)
  end



  # if session is W, then Winter
  # if session is S, then Summer
  def expected_first_atp sisCourse
    session = sisCourse.crs_startterm.slice(/[A-Za-z]/)
    if session.upcase == "W"
      "Winter"
    elsif session.upcase == "S"
      "Summer"
    end    
  end

  def std_duration_time_quantity sisCourse
    if sisCourse.crs_credit == nil
      12
    elsif sisCourse.crs_credit.to_i <= 3
      12
    else
      24
    end
  end

  def year_from_term term
    term.slice(/[\d]*/)
  end

  def expiration_date sisCourse

    if sisCourse.crs_endterm

      year = year_from_term(sisCourse.crs_endterm).to_i
      Date.new(year, 12, 31)
    else
      ""
    end
  end

  def level sisCourse
    sisCourse.crs_no ? sisCourse.crs_no.slice(0,1) + "00" : ""      
  end



# Requires an array of courses to pass to the LU Service
  def create_courses courses
    success = []
    failed = []
    File.open("create_courses-#{Time.now.to_i}.log", "w") do |file|
      file.puts "Creating Courses"
      file.puts "--------------------"
      file.puts Time.now

      courses.each do |sisCourse|
        begin
          file.puts "-------------------------------------------------------------------------"
          file.puts "Calling CreateCLU"
          file.puts "-------------------------------------------------------------------------"
          response = create_course(sisCourse) do |soap|
            file.puts "--------------------"
            file.puts "SOAP Request"
            file.puts "--------------------"
            file.puts soap.body.to_soap_xml
          end
          file.puts "--------------------"
          file.puts "SOAP Response"
          file.puts "--------------------"
          file.puts response

          success << sisCourse

        rescue Exception => e
          failed << sisCourse
          file.puts e.message
          file.puts e.backtrace
        end
      end
      file.puts "Summary of Course Migration"
      file.puts "Total Courses: #{courses.size}"
      file.puts "Number of courses migrated: #{success.size}"
      file.puts "Number of failed migrations: #{failed.size}"

      if failed.size > 0
        file.puts "Failed Courses"
        file.puts "--------------"
        failed.each do |sisCourse|
          file.puts course_error_message sisCourse
        end
      end
    end
  end

  # creates the error message for a course
  def course_error_message sisCourse
    "Code: #{sisCourse.crs_code}, Seq No:#{sisCourse.crs_seq_no}, Ver Seq: #{sisCourse.crs_ver_seq}"
  end
end

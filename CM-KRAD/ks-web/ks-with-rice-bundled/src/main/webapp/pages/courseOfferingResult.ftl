<table class='uif-tableCollectionLayout dataTable'>
    <thead>
    <th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': activate to sort column ascending'></th>
    <th scope='col' colspan='1' rowspan='1' class='sorting_asc' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-sort='ascending' aria-label=': activate to sort column descending'></th>
    <th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Status'>Status</th>
    <th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Title'>Title</th>
    <th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Credits'>Credits</th>
    <th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Grading'>Graidng</th>
    </thead>

    <tbody>
    <#list tableData as tData>
        <tr>
            <td></td>
            <td>
                <a id='code_line${tData_index}' href='${url}/inquiry?courseOfferingId=${tData.courseOfferingId}&amp;methodToCall=start&amp;dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper&amp;renderedInLightBox=true&amp;showHome=false&amp;showHistory=false&amp;history=null'
                   target='_self' class='uif-link' title='Course Offering =courseOfferingId'>${tData.courseOfferingCode}</a>
            </td>
            <td>
                ${tData.courseOfferingStateDisplay}
            </td>
            <td>
                ${tData.courseOfferingDesc}
            </td>
            <td>
                ${tData.courseOfferingCreditOptionDisplay}
            </td>
            <td>
                ${tData.courseOfferingGradingOptionDisplay}
            </td>
        </tr>
    </#list>
    </tbody>

    <tfoot>
    <th rowspan='1' colspan='1' ></th>
    <th rowspan='1' colspan='1' ></th>
    <th rowspan='1' colspan='1' ></th>
    <th rowspan='1' colspan='1' ></th>
    <th rowspan='1' colspan='1' ></th>
    <th rowspan='1' colspan='1' ></th>
    </tfoot>
</table>
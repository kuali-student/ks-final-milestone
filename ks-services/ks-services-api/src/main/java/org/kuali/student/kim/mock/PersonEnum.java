/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.mock;

/**
 * @author nwright
 */
public enum PersonEnum {

    ADMINID1("STAFF", "888880001", "adminId1", "rcurry@kuali.edu", "adminId1", "rcurry", true, "Registrar", "Robin", "G", "Curry", "Female", "Ms.", "", "214 East 42nd Place", "Raynham", "MA", "02767"),
    ADMINID2("STAFF", "888880002", "adminId2", "wmendez@kuali.edu", "adminId2", "wmendez", true, "Registrar", "Willie", "O", "Mendez", "Female", "Ms.", "", "232 Ridge Top Circle", "Lexington", "MA", "02277"),
    ADMINID3("STAFF", "888880003", "adminId3", "msherman@kuali.edu", "adminId3", "msherman", true, "FinancialAid", "Melody", "A", "Sherman", "Female", "Ms.", "", "205 Spruce Lane", "New Bedford", "MA", "02742"),
    ADMINID4("STAFF", "888880004", "adminId4", "eharper@kuali.edu", "adminId4", "eharper", true, "StudentAccounts", "Edwin", "K", "Harper", "Male", "Mr.", "", "68 Morino Street", "Boston", "MA", "02117"),
    ADMINID5("STAFF", "888880005", "adminId5", "bbryant@kuali.edu", "adminId5", "bbryant", true, "Advising", "Billy", "K", "Bryant", "Male", "Dr.", "", "122 West 69th Court", "East Princeton", "MA", "01517"),
    ADMINID6("STAFF", "888880006", "adminId6", "tneal@kuali.edu", "adminId6", "tneal", true, "Admissions", "Tamara", "I", "Neal", "Female", "Ms.", "", "239 Lockheed Avenue", "South Egremont", "MA", "01258"),
    ADMINID7("STAFF", "888880007", "adminId7", "lfields@kuali.edu", "adminId7", "lfields", true, "Administration", "Lorraine", "H", "Fields", "Female", "Ms.", "", "263 Brenner Circle", "Bedford", "MA", "01730"),
    ADMINID8("STAFF", "888880008", "adminId8", "ljenkins@kuali.edu", "adminId8", "ljenkins", true, "English", "Lloyd", "D", "Jenkins", "Male", "Mr.", "", "15 West 34th Avenue", "Westport", "MA", "02790"),
    ADMINID9("STAFF", "888880009", "adminId9", "npotter@kuali.edu", "adminId9", "npotter", true, "FineArts", "Natasha", "I", "Potter", "Female", "Ms.", "", "260 Tom White Circle", "Drury", "MA", "01343"),
    ADMINID10("STAFF", "888880010", "adminId10", "afrost@kuali.edu", "adminId10", "afrost", true, "Advising", "Arthur", "X", "Frost", "Male", "Dr.", "III", "43 Harp Circle", "North Carver", "MA", "02355"),
    ADMINID11("STAFF", "888880011", "adminId11", "plawson@kuali.edu", "adminId11", "plawson", true, "AfricanAmericanStudies", "Paula", "S", "Lawson", "Female", "Ms.", "", "54 Wright Street", "North Dighton", "MA", "02764"),
    ADMINID12("STAFF", "888880012", "adminId12", "jholt@kuali.edu", "adminId12", "jholt", true, "AnimalAvianSciences", "Jessica", "E", "Holt", "Female", "Ms.", "", "267 East 41st Court", "North Pembroke", "MA", "02358"),
    ADMINID13("STAFF", "888880013", "adminId13", "jstewart@kuali.edu", "adminId13", "jstewart", true, "Athletics", "Joann", "M", "Stewart", "Female", "Ms.", "", "171 Wingate Circle", "Newton Lower Falls", "MA", "02162"),
    ADMINID14("STAFF", "888880014", "adminId14", "bbaker@kuali.edu", "adminId14", "bbaker", true, "Biochemistry", "Bradley", "E", "Baker", "Male", "Mr.", "Jr.", "90 Chilkat Court", "Amherst", "MA", "01002"),
    ADMINID15("STAFF", "888880015", "adminId15", "wross@kuali.edu", "adminId15", "wross", true, "Registrar", "Wendy", "M", "Ross", "Female", "Ms.", "", "127 West 83Rd Avenue", "Dalton", "MA", "01226"),
    ADMINID16("STAFF", "888880016", "adminId16", "ksullivan@kuali.edu", "adminId16", "ksullivan", true, "FinancialAid", "Kim", "B", "Sullivan", "Female", "Ms.", "", "221 Brooks Road", "Sunderland", "MA", "01375"),
    ADMINID17("STAFF", "888880017", "adminId17", "hroman@kuali.edu", "adminId17", "hroman", true, "StudentAccounts", "Harry", "P", "Roman", "Male", "Mr.", "", "265 Hillhaven Circle", "East Dennis", "MA", "02641"),
    ADMINID18("STAFF", "888880018", "adminId18", "mgallegos@kuali.edu", "adminId18", "mgallegos", true, "Advising", "Misty", "N", "Gallegos", "Female", "Ms.", "", "213 Sarichef Loop", "Orange", "MA", "01355"),
    ADMINID19("STAFF", "888880019", "adminId19", "rpeterson@kuali.edu", "adminId19", "rpeterson", true, "Chemistry", "Ramon", "B", "Peterson", "Male", "Mr.", "", "205 Whitefish Circle", "Worcester", "MA", "01603"),
    ADMINID20("STAFF", "888880020", "adminId20", "bfisher@kuali.edu", "adminId20", "bfisher", true, "Civil", "Beverly", "B", "Fisher", "Female", "Ms.", "", "286 Randamar Circle", "Tyngsboro", "MA", "01879"),
    ADMINID21("STAFF", "888880021", "adminId21", "sfrazier@kuali.edu", "adminId21", "sfrazier", true, "CompSci", "Stephen", "H", "Frazier", "Male", "Mr.", "", "181 Wesleyan Drive", "South Weymouth", "MA", "02190"),
    ADMINID22("STAFF", "888880022", "adminId22", "jchavez@kuali.edu", "adminId22", "jchavez", true, "CrimCrimJus", "Julia", "G", "Chavez", "Female", "Ms.", "", "121 Caribou Avenue", "Boston", "MA", "02103"),
    ADMINID23("STAFF", "888880023", "adminId23", "fpearson@kuali.edu", "adminId23", "fpearson", true, "Economics", "Floyd", "J", "Pearson", "Male", "Mr.", "", "40 Kenny Place", "Housatonic", "MA", "01236"),
    ADMINID24("STAFF", "888880024", "adminId24", "fmorrow@kuali.edu", "adminId24", "fmorrow", true, "English", "Frank", "F", "Morrow", "Male", "Mr.", "IIV", "64 Woodcliff Court", "Centerville", "MA", "02636"),
    ADMINID25("STAFF", "888880025", "adminId25", "eperez@kuali.edu", "adminId25", "eperez", true, "FineArts", "Esther", "G", "Perez", "Female", "Ms.", "", "257 Anchor Park Circle", "Wellfleet", "MA", "02667"),
    ADMINID26("STAFF", "888880026", "adminId26", "fcampbell@kuali.edu", "adminId26", "fcampbell", true, "French", "Frederick", "R", "Campbell", "Male", "Mr.", "", "29 Chaimi Loop", "Rowe", "MA", "01367"),
    ADMINID27("STAFF", "888880027", "adminId27", "kberry@kuali.edu", "adminId27", "kberry", true, "Geography", "Kelli", "U", "Berry", "Female", "Ms.", "", "100 Beaufort Way", "Worcester", "MA", "01654"),
    ADMINID28("STAFF", "888880028", "adminId28", "wadams@kuali.edu", "adminId28", "wadams", true, "Geology", "Wayne", "V", "Adams", "Male", "Mr.", "", "143 Doggie Avenue", "Dunstable", "MA", "01827"),
    ADMINID29("STAFF", "888880029", "adminId29", "rwalters@kuali.edu", "adminId29", "rwalters", true, "History", "Rodney", "Q", "Walters", "Male", "Mr.", "", "176 Ridge Park Drive", "Allston", "MA", "02134"),
    ADMINID30("STAFF", "888880030", "adminId30", "dhouston@kuali.edu", "adminId30", "dhouston", true, "Linguistics", "Deanna", "O", "Houston", "Female", "Ms.", "", "259 Brooke Drive", "Montague", "MA", "01351"),
    ADMINID31("STAFF", "888880031", "adminId31", "bcollins@kuali.edu", "adminId31", "bcollins", true, "Mathematics", "Brandon", "Y", "Collins", "Male", "Mr.", "", "217 Nystrom Street", "Forestdale", "MA", "02644"),
    ADMINID32("STAFF", "888880032", "adminId32", "tbarnes@kuali.edu", "adminId32", "tbarnes", true, "Mechanical", "Tina", "Q", "Barnes", "Female", "Dr.", "", "216 Loganberry Street", "Boston", "MA", "02203"),
    ADMINID33("STAFF", "888880033", "adminId33", "freed@kuali.edu", "adminId33", "freed", true, "Music", "Frances", "P", "Reed", "Female", "Ms.", "", "138 Hanley Circle", "Boston", "MA", "02183"),
    ADMINID34("STAFF", "888880034", "adminId34", "aluna@kuali.edu", "adminId34", "aluna", true, "Philosophy", "Alan", "H", "Luna", "Male", "Mr.", "", "150 Kogru Place", "Boston", "MA", "02101"),
    ADMINID35("STAFF", "888880035", "adminId35", "kcaldwell@kuali.edu", "adminId35", "kcaldwell", true, "Physics", "Kristin", "H", "Caldwell", "Female", "Ms.", "", "221 Headlands Circle", "Worcester", "MA", "01612"),
    ADMINID36("STAFF", "888880036", "adminId36", "jgraham@kuali.edu", "adminId36", "jgraham", true, "PolySci", "Jessie", "H", "Graham", "Female", "Ms.", "", "158 California Creek Way", "Worcester", "MA", "01607"),
    ADMINID37("STAFF", "888880037", "adminId37", "rknight@kuali.edu", "adminId37", "rknight", true, "Psychology", "Ray", "K", "Knight", "Male", "Mr.", "", "261 East 101st Avenue", "Nonantum", "MA", "02195"),
    ADMINID38("STAFF", "888880038", "adminId38", "sray@kuali.edu", "adminId38", "sray", true, "Sociology", "Suzanne", "S", "Ray", "Female", "Ms.", "", "164 Deerfield Drive", "Oakham", "MA", "01068"),
    ADMINID39("STAFF", "888880039", "adminId39", "cstevenson@kuali.edu", "adminId39", "cstevenson", true, "FineArts", "Carl", "S", "Stevenson", "Male", "Mr.", "", "295 Dale Street", "New Bedford", "MA", "02740"),
    ADMINID40("STAFF", "888880040", "adminId40", "pbowman@kuali.edu", "adminId40", "pbowman", true, "French", "Pedro", "L", "Bowman", "Male", "Dr.", "", "176 Wanigan Street", "Berlin", "MA", "01503"),
    ADMINID41("STAFF", "888880041", "adminId41", "ndunn@kuali.edu", "adminId41", "ndunn", true, "Geography", "Natalie", "L", "Dunn", "Female", "Ms.", "", "32 Lynx Way", "Jamaica Plain", "MA", "02130"),
    ADMINID42("STAFF", "888880042", "adminId42", "sjohns@kuali.edu", "adminId42", "sjohns", true, "Geology", "Shawna", "Q", "Johns", "Female", "Ms.", "", "52 Whiteney Circle", "Ayer", "MA", "01432"),
    ADMINID43("STAFF", "888880043", "adminId43", "bramsey@kuali.edu", "adminId43", "bramsey", true, "History", "Billy", "V", "Ramsey", "Male", "Mr.", "", "214 West 73Rd Circle", "Wrentham", "MA", "02093"),
    ADMINID44("STAFF", "888880044", "adminId44", "ebailey@kuali.edu", "adminId44", "ebailey", true, "Linguistics", "Edgar", "Y", "Bailey", "Male", "Mr.", "", "187 Heide Drive", "Windsor", "MA", "01270"),
    ADMINID45("STAFF", "888880045", "adminId45", "mgreer@kuali.edu", "adminId45", "mgreer", true, "Mathematics", "Margie", "H", "Greer", "Female", "Ms.", "", "78 Banff Circle", "Erving", "MA", "01344"),
    ADMINID46("STAFF", "888880046", "adminId46", "eelliott@kuali.edu", "adminId46", "eelliott", true, "Mechanical", "Ella", "D", "Elliott", "Female", "Ms.", "", "35 Hillside Drive", "Spencer", "MA", "01562"),
    ADMINID47("STAFF", "888880047", "adminId47", "mfleming@kuali.edu", "adminId47", "mfleming", true, "Music", "Melinda", "Q", "Fleming", "Female", "Ms.", "", "113 East 18th Circle", "Savoy", "MA", "01256"),
    ADMINID48("STAFF", "888880048", "adminId48", "ljohnson@kuali.edu", "adminId48", "ljohnson", true, "Philosophy", "Lisa", "R", "Johnson", "Female", "Ms.", "", "201 Starflower Circle", "Conway", "MA", "01341"),
    ADMINID49("STAFF", "888880049", "adminId49", "msaunders@kuali.edu", "adminId49", "msaunders", true, "Physics", "Molly", "K", "Saunders", "Female", "Ms.", "", "268 Arlington Drive North", "Shelburne Falls", "MA", "01370"),
    ADMINID50("STAFF", "888880050", "adminId50", "dyates@kuali.edu", "adminId50", "dyates", true, "PolySci", "Derek", "J", "Yates", "Male", "Mr.", "", "290 Earl Court", "Westborough", "MA", "01580"),
    ADMINID51("STAFF", "888880051", "adminId51", "fwood@kuali.edu", "adminId51", "fwood", true, "Psychology", "Florence", "P", "Wood", "Female", "Ms.", "", "241 East 10th Avenue", "Berkley", "MA", "02779"),
    ADMINID52("STAFF", "888880052", "adminId52", "scole@kuali.edu", "adminId52", "scole", true, "Athletics", "Shirley", "R", "Cole", "Female", "Ms.", "", "1 Thomas Road", "Concord", "MA", "01742"),
    ADMINID53("STAFF", "888880053", "adminId53", "kstokes@kuali.edu", "adminId53", "kstokes", true, "Biochemistry", "Keith", "T", "Stokes", "Male", "Mr.", "", "255 Citadel Lane", "Gardner", "MA", "01441"),
    ADMINID54("STAFF", "888880054", "adminId54", "tsimon@kuali.edu", "adminId54", "tsimon", true, "Biology", "Toni", "K", "Simon", "Female", "Ms.", "", "95 Camelot Circle", "Stockbridge", "MA", "01262"),
    ADMINID55("STAFF", "888880055", "adminId55", "triley@kuali.edu", "adminId55", "triley", true, "Botany", "Tracey", "X", "Riley", "Male", "Mr.", "", "36 Moody Street", "Fall River", "MA", "02722"),
    ADMINID56("STAFF", "888880056", "adminId56", "nkramer@kuali.edu", "adminId56", "nkramer", true, "CareerServices", "Nora", "A", "Kramer", "Female", "Ms.", "", "245 Prominence Pointe Drive", "Boston", "MA", "02104"),
    ADMINID57("STAFF", "888880057", "adminId57", "fgardner@kuali.edu", "adminId57", "fgardner", true, "Chemical", "Frank", "A", "Gardner", "Male", "Mr.", "", "265 Bitterroot Circle", "Cuttyhunk", "MA", "02713"),
    ADMINID58("STAFF", "888880058", "adminId58", "jharris@kuali.edu", "adminId58", "jharris", true, "Chemistry", "James", "R", "Harris", "Male", "Mr.", "", "120 Jack Street", "Franklin", "MA", "02038"),
    ADMINID59("STAFF", "888880059", "adminId59", "kpatterson@kuali.edu", "adminId59", "kpatterson", true, "Civil", "Kathleen", "U", "Patterson", "Female", "Ms.", "", "40 Ruth Street", "South Hadley", "MA", "01075"),
    ADMINID60("STAFF", "888880060", "adminId60", "clowe@kuali.edu", "adminId60", "clowe", true, "CompSci", "Claudia", "R", "Lowe", "Female", "Ms.", "", "160 Chasewood Lane", "Pocasset", "MA", "02559"),
    ADMINID61("STAFF", "888880061", "adminId61", "rfrank@kuali.edu", "adminId61", "rfrank", true, "French", "Robert", "E", "Frank", "Male", "Mr.", "", "128 Glenview Street", "Chilmark", "MA", "02535"),
    ADMINID62("STAFF", "888880062", "adminId62", "smartinez@kuali.edu", "adminId62", "smartinez", true, "French", "Sharon", "Y", "Martinez", "Female", "Ms.", "", "76 Briny Circle", "Newburyport", "MA", "01950"),
    INSTRUCTORID1("FCLTY", "999990001", "instructorId1", "pmiles@kuali.edu", "instructorId1", "pmiles", true, "Advising", "Perry", "O", "Miles", "Male", "Mr.", "", "87 Bryant Circle", "Lynn", "MA", "01904"),
    INSTRUCTORID2("FCLTY", "999990002", "instructorId2", "drobinson@kuali.edu", "instructorId2", "drobinson", true, "AfricanAmericanStudies", "Daniel", "B", "Robinson", "Male", "Mr.", "", "198 Aircraft Drive", "Great Barrington", "MA", "01230"),
    INSTRUCTORID3("FCLTY", "999990003", "instructorId3", "chaynes@kuali.edu", "instructorId3", "chaynes", true, "AnimalAvianSciences", "Christopher", "R", "Haynes", "Male", "Mr.", "", "162 Spruce Street", "Nutting Lake", "MA", "01865"),
    INSTRUCTORID4("FCLTY", "999990004", "instructorId4", "hhicks@kuali.edu", "instructorId4", "hhicks", true, "Athletics", "Hector", "U", "Hicks", "Male", "Mr.", "", "69 North Boniface Parkway", "Hingham", "MA", "02043"),
    INSTRUCTORID5("FCLTY", "999990005", "instructorId5", "mhowell@kuali.edu", "instructorId5", "mhowell", true, "Biochemistry", "Matthew", "M", "Howell", "Male", "Dr.", "", "257 Cobblecreek Circle", "West Bridgewater", "MA", "02379"),
    INSTRUCTORID6("FCLTY", "999990006", "instructorId6", "sjones@kuali.edu", "instructorId6", "sjones", true, "Biology", "Samuel", "B", "Jones", "Male", "Mr.", "", "40 Dickson Road", "Rockland", "MA", "02370"),
    INSTRUCTORID7("FCLTY", "999990007", "instructorId7", "apeterson@kuali.edu", "instructorId7", "apeterson", true, "Botany", "Angela", "D", "Peterson", "Female", "Ms.", "", "242 Palos Verdes Circle", "South Carver", "MA", "02366"),
    INSTRUCTORID8("FCLTY", "999990008", "instructorId8", "rtate@kuali.edu", "instructorId8", "rtate", true, "CareerServices", "Roxanne", "S", "Tate", "Female", "Ms.", "", "165 Timberlane Circle", "Springfield", "MA", "01106"),
    INSTRUCTORID9("FCLTY", "999990009", "instructorId9", "jfinley@kuali.edu", "instructorId9", "jfinley", true, "Chemical", "Jesse", "N", "Finley", "Male", "Mr.", "", "246 Potter Crest Circle", "Boston", "MA", "02294"),
    INSTRUCTORID10("FCLTY", "999990010", "instructorId10", "dmorgan@kuali.edu", "instructorId10", "dmorgan", true, "Chemistry", "Dennis", "V", "Morgan", "Male", "Mr.", "", "18 Glacier Bay Circle", "West Boxford", "MA", "01885"),
    INSTRUCTORID11("FCLTY", "999990011", "instructorId11", "kmccoy@kuali.edu", "instructorId11", "kmccoy", true, "Civil", "Karl", "R", "McCoy", "Male", "Mr.", "", "206 Constitution Street", "Cataumet", "MA", "02534"),
    INSTRUCTORID12("FCLTY", "999990012", "instructorId12", "vkelley@kuali.edu", "instructorId12", "vkelley", true, "CompSci", "Vickie", "S", "Kelley", "Female", "Ms.", "", "108 Seville Park Circle", "Northborough", "MA", "01532"),
    INSTRUCTORID13("FCLTY", "999990013", "instructorId13", "cvasquez@kuali.edu", "instructorId13", "cvasquez", true, "CrimCrimJus", "Cecilia", "G", "Vasquez", "Female", "Ms.", "", "198 West 70th Circle", "Boston", "MA", "02217"),
    INSTRUCTORID14("FCLTY", "999990014", "instructorId14", "oterry@kuali.edu", "instructorId14", "oterry", true, "Economics", "Opal", "I", "Terry", "Female", "Ms.", "", "211 Travis Street", "North Scituate", "MA", "02060"),
    INSTRUCTORID15("FCLTY", "999990015", "instructorId15", "rstephens@kuali.edu", "instructorId15", "rstephens", true, "English", "Regina", "Q", "Stephens", "Female", "Ms.", "", "205 Sarabella Lane", "Essex", "MA", "01929"),
    INSTRUCTORID16("FCLTY", "999990016", "instructorId16", "lwall@kuali.edu", "instructorId16", "lwall", true, "FineArts", "Lindsay", "I", "Wall", "Female", "Ms.", "", "132 Laurel Street", "Richmond", "MA", "01254"),
    INSTRUCTORID17("FCLTY", "999990017", "instructorId17", "eballard@kuali.edu", "instructorId17", "eballard", true, "French", "Eunice", "O", "Ballard", "Female", "Ms.", "", "89 East 16th Avenue", "Ashley Falls", "MA", "01222"),
    INSTRUCTORID18("FCLTY", "999990018", "instructorId18", "lweaver@kuali.edu", "instructorId18", "lweaver", true, "Geography", "Lucille", "R", "Weaver", "Female", "Ms.", "", "39 Shady Bay Circle", "North Brookfield", "MA", "01535"),
    INSTRUCTORID19("FCLTY", "999990019", "instructorId19", "jphillips@kuali.edu", "instructorId19", "jphillips", true, "Geology", "Janice", "G", "Phillips", "Female", "Ms.", "", "28 Minnesota Drive", "Woburn", "MA", "01814"),
    INSTRUCTORID20("FCLTY", "999990020", "instructorId20", "crice@kuali.edu", "instructorId20", "crice", true, "History", "Charlotte", "A", "Rice", "Female", "Ms.", "", "43 Rebischke Lane", "Boston", "MA", "02108"),
    INSTRUCTORID21("FCLTY", "999990021", "instructorId21", "gporter@kuali.edu", "instructorId21", "gporter", true, "Linguistics", "Glenda", "Q", "Porter", "Female", "Ms.", "", "266 Butte Circle", "Arlington Heights", "MA", "02175"),
    INSTRUCTORID22("FCLTY", "999990022", "instructorId22", "egray@kuali.edu", "instructorId22", "egray", true, "Mathematics", "Eric", "M", "Gray", "Male", "Mr.", "", "97 Summerset Drive", "Cambridge", "MA", "02142"),
    INSTRUCTORID23("FCLTY", "999990023", "instructorId23", "ddennis@kuali.edu", "instructorId23", "ddennis", true, "Mechanical", "Doris", "E", "Dennis", "Female", "Ms.", "", "49 Alpine Court", "Palmer", "MA", "01069"),
    INSTRUCTORID24("FCLTY", "999990024", "instructorId24", "mlong@kuali.edu", "instructorId24", "mlong", true, "Music", "Michelle", "Q", "Long", "Female", "Ms.", "", "142 Sundi Way", "Boston", "MA", "02296"),
    INSTRUCTORID25("FCLTY", "999990025", "instructorId25", "dhorton@kuali.edu", "instructorId25", "dhorton", true, "Philosophy", "Dolores", "Q", "Horton", "Female", "Ms.", "", "151 East Loop Road", "Edgartown", "MA", "02539"),
    INSTRUCTORID26("FCLTY", "999990026", "instructorId26", "nevans@kuali.edu", "instructorId26", "nevans", true, "Physics", "Norma", "P", "Evans", "Female", "Ms.", "", "143 Laurence Court", "Cohasset", "MA", "02025"),
    INSTRUCTORID27("FCLTY", "999990027", "instructorId27", "mwilliamson@kuali.edu", "instructorId27", "mwilliamson", true, "PolySci", "Miguel", "H", "Williamson", "Male", "Mr.", "", "131 Timberwolf Circle", "Lenox", "MA", "01240"),
    INSTRUCTORID28("FCLTY", "999990028", "instructorId28", "gjennings@kuali.edu", "instructorId28", "gjennings", true, "Psychology", "Gerald", "O", "Jennings", "Male", "Mr.", "", "20 J-K Lane", "Cambridge", "MA", "02140"),
    INSTRUCTORID29("FCLTY", "999990029", "instructorId29", "bmeyer@kuali.edu", "instructorId29", "bmeyer", true, "Sociology", "Betsy", "Q", "Meyer", "Female", "Ms.", "", "220 Camelot Drive", "West Barnstable", "MA", "02668"),
    INSTRUCTORID30("FCLTY", "999990030", "instructorId30", "mwells@kuali.edu", "instructorId30", "mwells", true, "AfricanAmericanStudies", "Manuel", "T", "Wells", "Male", "Mr.", "", "91 Emerald Street", "Millis", "MA", "02054"),
    INSTRUCTORID31("FCLTY", "999990031", "instructorId31", "mmcclain@kuali.edu", "instructorId31", "mmcclain", true, "AnimalAvianSciences", "Madeline", "M", "McClain", "Female", "Ms.", "", "97 East 66th Avenue", "Roxbury", "MA", "02120"),
    INSTRUCTORID32("FCLTY", "999990032", "instructorId32", "pmorris@kuali.edu", "instructorId32", "pmorris", true, "Athletics", "Phillip", "P", "Morris", "Male", "Mr.", "", "16 Cutlass Circle", "Somerville", "MA", "02145"),
    INSTRUCTORID33("FCLTY", "999990033", "instructorId33", "jhancock@kuali.edu", "instructorId33", "jhancock", true, "Biochemistry", "James", "D", "Hancock", "Male", "Mr.", "", "239 Wren Circle", "Becket", "MA", "01223"),
    INSTRUCTORID34("FCLTY", "999990034", "instructorId34", "jrivas@kuali.edu", "instructorId34", "jrivas", true, "Biology", "Jeff", "R", "Rivas", "Male", "Mr.", "", "78 Wood River Way", "Cambridge", "MA", "02141"),
    INSTRUCTORID35("FCLTY", "999990035", "instructorId35", "tbaxter@kuali.edu", "instructorId35", "tbaxter", true, "Botany", "Tara", "V", "Baxter", "Female", "Ms.", "", "74 Winners Circle", "North Adams", "MA", "01247"),
    INSTRUCTORID36("FCLTY", "999990036", "instructorId36", "ewood@kuali.edu", "instructorId36", "ewood", true, "CareerServices", "Elmer", "Q", "Wood", "Male", "Mr.", "", "60 Kensington Circle", "Groton", "MA", "01471"),
    INSTRUCTORID37("FCLTY", "999990037", "instructorId37", "ssutton@kuali.edu", "instructorId37", "ssutton", true, "Chemical", "Steve", "G", "Sutton", "Male", "Mr.", "", "42 Teri Circle", "Worcester", "MA", "01600"),
    INSTRUCTORID38("FCLTY", "999990038", "instructorId38", "charvey@kuali.edu", "instructorId38", "charvey", true, "Chemistry", "Carmen", "M", "Harvey", "Female", "Ms.", "", "21 Trapline Circle", "Brant Rock", "MA", "02020"),
    INSTRUCTORID39("FCLTY", "999990039", "instructorId39", "cgriffith@kuali.edu", "instructorId39", "cgriffith", true, "Civil", "Candace", "V", "Griffith", "Female", "Ms.", "", "54 Chandelle Circle", "Springfield", "MA", "01118"),
    INSTRUCTORID40("FCLTY", "999990040", "instructorId40", "crivera@kuali.edu", "instructorId40", "crivera", true, "CompSci", "Christina", "V", "Rivera", "Female", "Ms.", "", "279 Seal Point Circle", "East Otis", "MA", "01029"),
    INSTRUCTORID41("FCLTY", "999990041", "instructorId41", "rstewart@kuali.edu", "instructorId41", "rstewart", true, "CrimCrimJus", "Ryan", "I", "Stewart", "Male", "Mr.", "", "157 Dee Circle", "Marlborough", "MA", "01752"),
    INSTRUCTORID42("FCLTY", "999990042", "instructorId42", "dbarrett@kuali.edu", "instructorId42", "dbarrett", true, "Economics", "Debra", "Q", "Barrett", "Female", "Ms.", "", "261 Norene Street", "Dover", "MA", "02030"),
    INSTRUCTORID43("FCLTY", "999990043", "instructorId43", "dgray@kuali.edu", "instructorId43", "dgray", true, "English", "Dianna", "A", "Gray", "Female", "Ms.", "", "85 Duben Avenue", "Onset", "MA", "02558"),
    INSTRUCTORID44("FCLTY", "999990044", "instructorId44", "swillis@kuali.edu", "instructorId44", "swillis", true, "FineArts", "Sophia", "O", "Willis", "Female", "Ms.", "", "39 Telder Street", "Northfield", "MA", "01360"),
    INSTRUCTORID45("FCLTY", "999990045", "instructorId45", "jfranklin@kuali.edu", "instructorId45", "jfranklin", true, "French", "Jamie", "E", "Franklin", "Female", "Ms.", "", "206 Talisman Road", "Worcester", "MA", "01653"),
    INSTRUCTORID46("FCLTY", "999990046", "instructorId46", "lfernandez@kuali.edu", "instructorId46", "lfernandez", true, "Geography", "Lawrence", "Z", "Fernandez", "Male", "Mr.", "", "67 Tamworth Circle", "Scituate", "MA", "02066"),
    INSTRUCTORID47("FCLTY", "999990047", "instructorId47", "grios@kuali.edu", "instructorId47", "grios", true, "Geology", "Glen", "S", "Rios", "Male", "Mr.", "", "159 Goose Berry Place", "Lawrence", "MA", "01841"),
    INSTRUCTORID48("FCLTY", "999990048", "instructorId48", "bfowler@kuali.edu", "instructorId48", "bfowler", true, "History", "Bobby", "H", "Fowler", "Male", "Mr.", "", "89 Eshamy Bay Drive", "Framingham", "MA", "01701"),
    INSTRUCTORID49("FCLTY", "999990049", "instructorId49", "rherrera@kuali.edu", "instructorId49", "rherrera", true, "Linguistics", "Ruby", "Q", "Herrera", "Female", "Ms.", "", "216 Trisha Avenue", "Boston", "MA", "02201"),
    INSTRUCTORID50("FCLTY", "999990050", "instructorId50", "njackson@kuali.edu", "instructorId50", "njackson", true, "Mathematics", "Nancy", "Q", "Jackson", "Female", "Ms.", "", "96 The Sun Loft Drive", "Middleboro", "MA", "02349"),
    INSTRUCTORID51("FCLTY", "999990051", "instructorId51", "rdavidson@kuali.edu", "instructorId51", "rdavidson", true, "Mechanical", "Raymond", "M", "Davidson", "Male", "Mr.", "", "273 Nash Street", "Middlesex Essex Gmf", "MA", "01889"),
    INSTRUCTORID52("FCLTY", "999990052", "instructorId52", "slane@kuali.edu", "instructorId52", "slane", true, "Music", "Shane", "D", "Lane", "Male", "Mr.", "", "37 Colchis Street", "West Medford", "MA", "02156"),
    INSTRUCTORID53("FCLTY", "999990053", "instructorId53", "rmueller@kuali.edu", "instructorId53", "rmueller", true, "Philosophy", "Raquel", "T", "Mueller", "Female", "Ms.", "", "237 Young Drive", "East Taunton", "MA", "02718"),
    INSTRUCTORID54("FCLTY", "999990054", "instructorId54", "rnunez@kuali.edu", "instructorId54", "rnunez", true, "Physics", "Randall", "M", "Nunez", "Male", "Mr.", "", "146 Atkinson Circle", "Hadley", "MA", "01035"),
    INSTRUCTORID55("FCLTY", "999990055", "instructorId55", "khill@kuali.edu", "instructorId55", "khill", true, "PolySci", "Karen", "I", "Hill", "Female", "Ms.", "", "2 Fisher Drive", "Haverhill", "MA", "01832"),
    INSTRUCTORID56("FCLTY", "999990056", "instructorId56", "hvargas@kuali.edu", "instructorId56", "hvargas", true, "Psychology", "Holly", "X", "Vargas", "Female", "Ms.", "", "54 Madigan Circle", "Brewster", "MA", "02631"),
    INSTRUCTORID57("FCLTY", "999990057", "instructorId57", "kalvarez@kuali.edu", "instructorId57", "kalvarez", true, "Sociology", "Kristine", "C", "Alvarez", "Female", "Ms.", "", "215 Brittany Place", "Athol", "MA", "01368"),
    INSTRUCTORID58("FCLTY", "999990058", "instructorId58", "jbass@kuali.edu", "instructorId58", "jbass", true, "AfricanAmericanStudies", "Jacquelyn", "D", "Bass", "Female", "Ms.", "", "91 Klutina Circle", "Dorchester", "MA", "02122"),
    INSTRUCTORID59("FCLTY", "999990059", "instructorId59", "bwagner@kuali.edu", "instructorId59", "bwagner", true, "AnimalAvianSciences", "Bradley", "G", "Wagner", "Male", "Mr.", "", "116 Amberwood Circle", "West Harwich", "MA", "02671"),
    INSTRUCTORID60("FCLTY", "999990060", "instructorId60", "mjimenez@kuali.edu", "instructorId60", "mjimenez", true, "Athletics", "Mike", "D", "Jimenez", "Male", "Mr.", "", "191 West 38th Place", "Revere", "MA", "02151"),
    STUDENTID1("STDNT", "777770001", "studentId1", "kstone@kuali.edu", "studentId1", "kstone", true, "Biochemistry", "Kara", "Q", "Stone", "Female", "Ms.", "", "11 Walrus Circle", "West Newton", "MA", "02165"),
    STUDENTID2("STDNT", "777770002", "studentId2", "bharris@kuali.edu", "studentId2", "bharris", true, "Biology", "Barbara", "A", "Harris", "Female", "Ms.", "", "35 Penny Circle", "Boston", "MA", "02299"),
    STUDENTID3("STDNT", "777770003", "studentId3", "criddle@kuali.edu", "studentId3", "criddle", true, "Botany", "Clifford", "I", "Riddle", "Male", "Mr.", "", "59 Atlantis Avenue", "Lynnfield", "MA", "01940"),
    STUDENTID4("STDNT", "777770004", "studentId4", "nwelch@kuali.edu", "studentId4", "nwelch", true, "CareerServices", "Nina", "X", "Welch", "Female", "Ms.", "", "66 Gina Circle", "East Boston", "MA", "02128"),
    STUDENTID5("STDNT", "777770005", "studentId5", "bmartin@kuali.edu", "studentId5", "bmartin", true, "Chemical", "Betty", "J", "Martin", "Female", "Ms.", "", "141 East 48th Avenue", "Boston", "MA", "02233"),
    STUDENTID6("STDNT", "777770006", "studentId6", "nmcdonald@kuali.edu", "studentId6", "nmcdonald", true, "Chemistry", "Nellie", "N", "McDonald", "Female", "Ms.", "", "144 Scenic Drive", "Boston", "MA", "02295"),
    STUDENTID7("STDNT", "777770007", "studentId7", "kthompson@kuali.edu", "studentId7", "kthompson", true, "Civil", "Kimberly", "K", "Thompson", "Female", "Ms.", "", "163 Ziemlak Circle", "Lee", "MA", "01264"),
    STUDENTID8("STDNT", "777770008", "studentId8", "ahopkins@kuali.edu", "studentId8", "ahopkins", true, "CompSci", "Amber", "D", "Hopkins", "Female", "Ms.", "", "178 Johnny Drive", "Boylston", "MA", "01505"),
    STUDENTID9("STDNT", "777770009", "studentId9", "jmanning@kuali.edu", "studentId9", "jmanning", true, "CrimCrimJus", "Johnny", "F", "Manning", "Male", "Mr.", "", "85 Rosella Street", "Brockton", "MA", "02401"),
    STUDENTID10("STDNT", "", "studentId10", "epittman@kuali.edu", "studentId10", "epittman", true, "Economics", "Eddie", "S", "Pittman", "Male", "Mr.", "", "178 Hazen Lane", "North Easton", "MA", "02356"),
    STUDENTID11("STDNT", "777770011", "studentId11", "tburton@kuali.edu", "studentId11", "tburton", true, "English", "Tracy", "P", "Burton", "Female", "Ms.", "", "154 North Bunn Street", "Winchester", "MA", "01890"),
    STUDENTID12("STDNT", "777770012", "studentId12", "gblake@kuali.edu", "studentId12", "gblake", true, "FineArts", "Gertrude", "S", "Blake", "Female", "Ms.", "", "141 Shadetree Circle", "Hanover", "MA", "02339"),
    STUDENTID13("STDNT", "777770013", "studentId13", "dmcclure@kuali.edu", "studentId13", "dmcclure", true, "French", "Donald", "S", "McClure", "Male", "Mr.", "", "35 M Court", "Lowell", "MA", "01853"),
    STUDENTID14("STDNT", "777770014", "studentId14", "jprince@kuali.edu", "studentId14", "jprince", true, "Geography", "Joel", "K", "Prince", "Male", "Mr.", "", "197 Tedrow Drive", "North Attleboro", "MA", "02763"),
    STUDENTID15("STDNT", "777770015", "studentId15", "sgonzales@kuali.edu", "studentId15", "sgonzales", true, "Geology", "Samantha", "A", "Gonzales", "Female", "Ms.", "", "46 Hampton Circle", "Chelsea", "MA", "02150"),
    STUDENTID16("STDNT", "777770016", "studentId16", "frussell@kuali.edu", "studentId16", "frussell", true, "History", "Francisco", "S", "Russell", "Male", "Mr.", "", "242 Klingler Street", "Sturbridge", "MA", "01566"),
    STUDENTID17("STDNT", "777770017", "studentId17", "aingram@kuali.edu", "studentId17", "aingram", true, "Linguistics", "Austin", "E", "Ingram", "Male", "Mr.", "", "217 Arne Erickson Circle", "Wales", "MA", "01081"),
    STUDENTID18("STDNT", "777770018", "studentId18", "rwhite@kuali.edu", "studentId18", "rwhite", true, "Mathematics", "Rebecca", "I", "White", "Female", "Ms.", "", "16 Bunker Street", "Webster", "MA", "01570"),
    STUDENTID19("STDNT", "777770019", "studentId19", "mpeters@kuali.edu", "studentId19", "mpeters", true, "Mechanical", "Mildred", "Q", "Peters", "Female", "Ms.", "", "126 South Windsor Circle", "Canton", "MA", "02021"),
    STUDENTID20("STDNT", "777770020", "studentId20", "tparker@kuali.edu", "studentId20", "tparker", true, "Music", "Timothy", "O", "Parker", "Male", "Mr.", "", "94 Tonsina Court", "Holliston", "MA", "01746"),
    STUDENTID21("STDNT", "777770021", "studentId21", "tdurham@kuali.edu", "studentId21", "tdurham", true, "Philosophy", "Thomas", "D", "Durham", "Male", "Mr.", "", "208 Bridle Lane", "Waltham", "MA", "02154"),
    STUDENTID22("STDNT", "777770022", "studentId22", "jmarsh@kuali.edu", "studentId22", "jmarsh", true, "Physics", "Judith", "S", "Marsh", "Female", "Ms.", "", "28 Scott Street", "West Brookfield", "MA", "01585"),
    STUDENTID23("STDNT", "777770023", "studentId23", "lyoung@kuali.edu", "studentId23", "lyoung", true, "PolySci", "Larry", "T", "Young", "Male", "Mr.", "", "220 Access Road A Road", "Boston", "MA", "02105"),
    STUDENTID24("STDNT", "777770024", "studentId24", "esims@kuali.edu", "studentId24", "esims", true, "Psychology", "Erica", "N", "Sims", "Female", "Ms.", "", "213 Windlass Circle", "Medfield", "MA", "02052"),
    STUDENTID25("STDNT", "777770025", "studentId25", "mmunoz@kuali.edu", "studentId25", "mmunoz", true, "Sociology", "Marvin", "M", "Munoz", "Male", "Mr.", "", "65 Lookout Circle", "South Egremont", "MA", "01252"),
    STUDENTID26("STDNT", "777770026", "studentId26", "kberry@kuali.edu", "studentId26", "kberry", true, "AfricanAmericanStudies", "Kelly", "E", "Berry", "Male", "Mr.", "", "13 Tarika Avenue", "Williamsburg", "MA", "01096"),
    STUDENTID27("STDNT", "777770027", "studentId27", "jflores@kuali.edu", "studentId27", "jflores", true, "AnimalAvianSciences", "Jack", "O", "Flores", "Male", "Mr.", "", "279 Jewel Street", "Medford", "MA", "02153"),
    STUDENTID28("STDNT", "777770028", "studentId28", "fhampton@kuali.edu", "studentId28", "fhampton", true, "Athletics", "Fred", "A", "Hampton", "Male", "Mr.", "", "98 O'Malley Road", "Woburn", "MA", "01801"),
    STUDENTID29("STDNT", "777770029", "studentId29", "khart@kuali.edu", "studentId29", "khart", true, "Biochemistry", "Kate", "O", "Hart", "Female", "Ms.", "", "160 Hansa Rose Circle", "Beverly", "MA", "01915"),
    STUDENTID30("STDNT", "777770030", "studentId30", "dvaughn@kuali.edu", "studentId30", "dvaughn", true, "Biology", "Doreen", "Q", "Vaughn", "Female", "Ms.", "", "290 Abbington Court", "Linwood", "MA", "01525"),
    STUDENTID31("STDNT", "777770031", "studentId31", "wbooker@kuali.edu", "studentId31", "wbooker", true, "Botany", "William", "A", "Booker", "Male", "Mr.", "", "6 Lesmer Circle", "North Falmouth", "MA", "02556"),
    STUDENTID32("STDNT", "777770032", "studentId32", "dwebb@kuali.edu", "studentId32", "dwebb", true, "CareerServices", "Don", "H", "Webb", "Male", "Mr.", "", "202 Solitude Circle", "East Weymouth", "MA", "02189"),
    STUDENTID33("STDNT", "777770033", "studentId33", "tlewis@kuali.edu", "studentId33", "tlewis", true, "Chemical", "Travis", "P", "Lewis", "Male", "Mr.", "", "113 Snowmass Circle", "Blandford", "MA", "01008"),
    STUDENTID34("STDNT", "777770034", "studentId34", "hvega@kuali.edu", "studentId34", "hvega", true, "Chemistry", "Hugh", "Q", "Vega", "Male", "Mr.", "", "207 King Street", "Centerville", "MA", "02634"),
    STUDENTID35("STDNT", "777770035", "studentId35", "aclayton@kuali.edu", "studentId35", "aclayton", true, "Civil", "Alison", "H", "Clayton", "Female", "Ms.", "", "116 Denise Circle", "Pittsfield", "MA", "01201"),
    STUDENTID36("STDNT", "777770036", "studentId36", "jmorris@kuali.edu", "studentId36", "jmorris", true, "CompSci", "Joshua", "P", "Morris", "Male", "Mr.", "", "120 Wellington Court", "Manchaug", "MA", "01526"),
    STUDENTID37("STDNT", "777770037", "studentId37", "vsimpson@kuali.edu", "studentId37", "vsimpson", true, "CrimCrimJus", "Victoria", "D", "Simpson", "Female", "Ms.", "", "165 West Dowling Road", "Sandwich", "MA", "02563"),
    STUDENTID38("STDNT", "777770038", "studentId38", "smckinney@kuali.edu", "studentId38", "smckinney", true, "Economics", "Stella", "B", "McKinney", "Female", "Ms.", "", "258 Kelly Maureen Circle", "North Attleboro", "MA", "02762"),
    STUDENTID39("STDNT", "777770039", "studentId39", "tporter@kuali.edu", "studentId39", "tporter", true, "English", "Todd", "Y", "Porter", "Male", "Mr.", "", "93 Buena Vista Drive", "Boston", "MA", "02116"),
    STUDENTID40("STDNT", "", "studentId40", "awilliams@kuali.edu", "studentId40", "awilliams", true, "FineArts", "Aaron", "V", "Williams", "Male", "Mr.", "", "129 West 94th Court", "Danvers", "MA", "01923"),
    STUDENTID41("STDNT", "777770041", "studentId41", "mgordon@kuali.edu", "studentId41", "mgordon", true, "French", "Megan", "A", "Gordon", "Female", "Ms.", "", "136 Meadow Wood Circle", "Bolton", "MA", "01740"),
    STUDENTID42("STDNT", "777770042", "studentId42", "kwoodard@kuali.edu", "studentId42", "kwoodard", true, "Geography", "Kay", "V", "Woodard", "Female", "Ms.", "", "183 Crete Street", "Newburyport", "MA", "01951"),
    STUDENTID43("STDNT", "777770043", "studentId43", "mwinters@kuali.edu", "studentId43", "mwinters", true, "Geology", "Micheal", "A", "Winters", "Male", "Mr.", "", "203 Mountain Way", "South Berlin", "MA", "01549"),
    STUDENTID44("STDNT", "777770044", "studentId44", "grice@kuali.edu", "studentId44", "grice", true, "History", "Gregory", "H", "Rice", "Male", "Mr.", "", "107 Turnagain Bluff Way", "Hopedale", "MA", "01747"),
    STUDENTID45("STDNT", "777770045", "studentId45", "jlawrence@kuali.edu", "studentId45", "jlawrence", true, "Linguistics", "Jeanette", "V", "Lawrence", "Female", "Ms.", "", "89 Moose Place", "Rockport", "MA", "01966"),
    STUDENTID46("STDNT", "777770046", "studentId46", "dgomez@kuali.edu", "studentId46", "dgomez", true, "Mathematics", "Darlene", "O", "Gomez", "Female", "Ms.", "", "140 Ursa Minor Circle", "Worthington", "MA", "01098"),
    STUDENTID47("STDNT", "777770047", "studentId47", "dburke@kuali.edu", "studentId47", "dburke", true, "Mechanical", "Dana", "S", "Burke", "Female", "Ms.", "", "175 Winter Ridge Court", "South Orleans", "MA", "02662"),
    STUDENTID48("STDNT", "777770048", "studentId48", "dlewis@kuali.edu", "studentId48", "dlewis", true, "Music", "Dorothy", "D", "Lewis", "Female", "Ms.", "", "201 Alyeska Avenue West", "East Orleans", "MA", "02643"),
    STUDENTID49("STDNT", "777770049", "studentId49", "sfitzgerald@kuali.edu", "studentId49", "sfitzgerald", true, "Philosophy", "Stephanie", "O", "Fitzgerald", "Female", "Ms.", "", "150 Salix Circle", "Worcester", "MA", "01610"),
    STUDENTID50("STDNT", "777770050", "studentId50", "gperry@kuali.edu", "studentId50", "gperry", true, "Physics", "Glenn", "Q", "Perry", "Male", "Mr.", "", "103 Chuck Drive", "South Yarmouth", "MA", "02664"),
    STUDENTID51("STDNT", "", "studentId51", "bdixon@kuali.edu", "studentId51", "bdixon", true, "PolySci", "Barry", "O", "Dixon", "Male", "Mr.", "", "149 Churchill Drive", "Norfolk", "MA", "02056"),
    STUDENTID52("STDNT", "777770052", "studentId52", "lwilkerson@kuali.edu", "studentId52", "lwilkerson", true, "Psychology", "Larry", "N", "Wilkerson", "Male", "Mr.", "", "230 Hardwood Court", "Bedford", "MA", "01731"),
    STUDENTID53("STDNT", "777770053", "studentId53", "cmorrison@kuali.edu", "studentId53", "cmorrison", true, "Sociology", "Calvin", "B", "Morrison", "Male", "Mr.", "", "255 Valarian Street", "Milton Village", "MA", "02187"),
    STUDENTID54("STDNT", "777770054", "studentId54", "jgriffin@kuali.edu", "studentId54", "jgriffin", true, "AfricanAmericanStudies", "Jenny", "Q", "Griffin", "Female", "Ms.", "", "128 Dare Avenue", "Georgetown", "MA", "01833"),
    STUDENTID55("STDNT", "777770055", "studentId55", "icampbell@kuali.edu", "studentId55", "icampbell", true, "AnimalAvianSciences", "Irene", "R", "Campbell", "Female", "Ms.", "", "99 East Eagle River Loop Road", "North Chelmsford", "MA", "01863"),
    STUDENTID56("STDNT", "777770056", "studentId56", "erios@kuali.edu", "studentId56", "erios", true, "Athletics", "Elsa", "J", "Rios", "Female", "Ms.", "", "183 Happy Lane", "Babson Park", "MA", "02157"),
    STUDENTID57("STDNT", "777770057", "studentId57", "aortiz@kuali.edu", "studentId57", "aortiz", true, "Biochemistry", "Alfred", "I", "Ortiz", "Male", "Mr.", "", "124 Tolsona Circle", "West Chatham", "MA", "02669"),
    STUDENTID58("STDNT", "777770058", "studentId58", "cmontgomery@kuali.edu", "studentId58", "cmontgomery", true, "Biology", "Carla", "S", "Montgomery", "Female", "Ms.", "", "177 West Tudor Road", "Harwich Port", "MA", "02646"),
    STUDENTID59("STDNT", "777770059", "studentId59", "ncummings@kuali.edu", "studentId59", "ncummings", true, "Botany", "Nathaniel", "O", "Cummings", "Male", "Mr.", "", "147 Monroe Avenue", "Dennis", "MA", "02638"),
    STUDENTID60("STDNT", "777770060", "studentId60", "swright@kuali.edu", "studentId60", "swright", true, "CareerServices", "Shawn", "A", "Wright", "Male", "Mr.", "", "252 Bow Circle", "Worcester", "MA", "01546"),
    STUDENTID61("STDNT", "777770061", "studentId61", "drobbins@kuali.edu", "studentId61", "drobbins", true, "Chemical", "Darren", "O", "Robbins", "Male", "Mr.", "", "238 Silver Spruce Drive", "Norwell", "MA", "02061"),
    STUDENTID62("STDNT", "777770062", "studentId62", "tpowell@kuali.edu", "studentId62", "tpowell", true, "Chemistry", "Terry", "O", "Powell", "Male", "Mr.", "", "241 Voyles Boulevard", "Buzzards Bay", "MA", "02542"),
    STUDENTID63("STDNT", "777770063", "studentId63", "pthompson@kuali.edu", "studentId63", "pthompson", true, "Civil", "Paul", "Q", "Thompson", "Male", "Mr.", "", "260 Elmendorf Drive", "Lawrence", "MA", "01842"),
    STUDENTID64("STDNT", "777770064", "studentId64", "chobbs@kuali.edu", "studentId64", "chobbs", true, "CompSci", "Clyde", "V", "Hobbs", "Male", "Mr.", "", "107 Turnagain Parkway", "East Walpole", "MA", "02032"),
    STUDENTID65("STDNT", "777770065", "studentId65", "cadams@kuali.edu", "studentId65", "cadams", true, "CrimCrimJus", "Christine", "R", "Adams", "Female", "Ms.", "", "286 Lennie Circle", "Middlesex Essex Gmf", "MA", "01807"),
    STUDENTID66("STDNT", "777770066", "studentId66", "jday@kuali.edu", "studentId66", "jday", true, "Economics", "Judy", "I", "Day", "Female", "Ms.", "", "9 Falling Water Circle", "West Somerville", "MA", "02144"),
    STUDENTID67("STDNT", "777770067", "studentId67", "scole@kuali.edu", "studentId67", "scole", true, "English", "Sam", "S", "Cole", "Male", "Mr.", "", "42 Oney Circle", "Prides Crossing", "MA", "01965"),
    STUDENTID68("STDNT", "777770068", "studentId68", "cmeyers@kuali.edu", "studentId68", "cmeyers", true, "FineArts", "Cristina", "O", "Meyers", "Female", "Ms.", "", "292 Anne Circle", "Williamstown", "MA", "01267"),
    STUDENTID69("STDNT", "777770069", "studentId69", "creed@kuali.edu", "studentId69", "creed", true, "French", "Carlos", "F", "Reed", "Male", "Mr.", "", "268 Wakefield Circle", "Rehoboth", "MA", "02769"),
    STUDENTID70("STDNT", "777770070", "studentId70", "cperkins@kuali.edu", "studentId70", "cperkins", true, "Geography", "Cathy", "K", "Perkins", "Female", "Ms.", "", "296 East 86th Avenue", "Glendale", "MA", "01229"),
    STUDENTID71("STDNT", "777770071", "studentId71", "mlittle@kuali.edu", "studentId71", "mlittle", true, "Geology", "Melanie", "Q", "Little", "Female", "Ms.", "", "158 Teri Drive", "Woods Hole", "MA", "02543"),
    STUDENTID72("STDNT", "777770072", "studentId72", "aphelps@kuali.edu", "studentId72", "aphelps", true, "History", "Alberta", "H", "Phelps", "Female", "Ms.", "", "204 Ephreta Court", "North Attleboro", "MA", "02761"),
    STUDENTID73("STDNT", "777770073", "studentId73", "abartlett@kuali.edu", "studentId73", "abartlett", true, "Linguistics", "Anthony", "I", "Bartlett", "Male", "Mr.", "", "185 Saint Elias Drive", "Chesterfield", "MA", "01012"),
    STUDENTID74("STDNT", "777770074", "studentId74", "kcastillo@kuali.edu", "studentId74", "kcastillo", true, "Mathematics", "Kevin", "C", "Castillo", "Male", "Mr.", "", "37 Heatherbrook Circle", "Boston", "MA", "02241"),
    STUDENTID75("STDNT", "777770075", "studentId75", "cbrown@kuali.edu", "studentId75", "cbrown", true, "Mechanical", "Chris", "P", "Brown", "Male", "Mr.", "", "22 Asterion Circle", "Newtonville", "MA", "02160"),
    STUDENTID76("STDNT", "777770076", "studentId76", "tharrison@kuali.edu", "studentId76", "tharrison", true, "Music", "Thelma", "M", "Harrison", "Female", "Ms.", "", "290 Castle Court", "Charlton Depot", "MA", "01509"),
    STUDENTID77("STDNT", "777770077", "studentId77", "tharmon@kuali.edu", "studentId77", "tharmon", true, "Philosophy", "Tom", "H", "Harmon", "Male", "Mr.", "", "243 Harrier Circle", "Chatham", "MA", "02633"),
    STUDENTID78("STDNT", "777770078", "studentId78", "llarson@kuali.edu", "studentId78", "llarson", true, "Physics", "Laurie", "I", "Larson", "Female", "Ms.", "", "83 Boeing Lane", "North Grafton", "MA", "01536"),
    STUDENTID79("STDNT", "777770079", "studentId79", "jhawkins@kuali.edu", "studentId79", "jhawkins", true, "PolySci", "Jody", "N", "Hawkins", "Female", "Ms.", "", "1 Idaho Street", "Yarmouth Port", "MA", "02675"),
    STUDENTID80("STDNT", "777770080", "studentId80", "mhardy@kuali.edu", "studentId80", "mhardy", true, "Psychology", "Marsha", "N", "Hardy", "Female", "Ms.", "", "130 Fountain Drive", "Springfield", "MA", "01152"),
    OTHERID1("AFLT", "666660001", "otherId1", "kingram@kuali.edu", "otherId1", "kingram", true, "Sociology", "Kayla", "J", "Ingram", "Female", "Ms.", "", "283 Boysen Berry Place", "Northampton", "MA", "01063"),
    OTHERID2("AFLT", "666660002", "otherId2", "pchristensen@kuali.edu", "otherId2", "pchristensen", true, "AfricanAmericanStudies", "Paul", "Q", "Christensen", "Male", "Mr.", "", "253 Echo Street", "Sheffield", "MA", "01257"),
    OTHERID3("AFLT", "", "otherId3", "jboyd@kuali.edu", "otherId3", "jboyd", true, "AnimalAvianSciences", "Jean", "A", "Boyd", "Female", "Ms.", "", "96 Hunter Drive", "Thorndike", "MA", "01079"),
    OTHERID4("AFLT", "", "otherId4", "cclark@kuali.edu", "otherId4", "cclark", true, "Athletics", "Carlos", "U", "Clark", "Male", "Mr.", "", "127 East 142nd Avenue", "Waverley", "MA", "02179"),
    OTHERID5("AFLT", "666660005", "otherId5", "mbyrd@kuali.edu", "otherId5", "mbyrd", true, "Biochemistry", "Mario", "H", "Byrd", "Male", "Mr.", "", "143 Neptune Court", "Hanson", "MA", "02341"),
    OTHERID6("AFLT", "", "otherId6", "dreynolds@kuali.edu", "otherId6", "dreynolds", true, "Biology", "Duane", "O", "Reynolds", "Male", "Mr.", "", "201 East Stonehill Drive", "Newton Highlands", "MA", "02161"),
    OTHERID7("AFLT", "", "otherId7", "fhood@kuali.edu", "otherId7", "fhood", true, "Botany", "Francis", "D", "Hood", "Female", "Ms.", "", "10 Rocky Cove Drive", "Colrain", "MA", "01340"),
    OTHERID8("AFLT", "666660008", "otherId8", "cwatson@kuali.edu", "otherId8", "cwatson", true, "CareerServices", "Curtis", "R", "Watson", "Male", "Mr.", "", "229 Fenn Street", "Boston", "MA", "02123"),
    OTHERID9("AFLT", "666660009", "otherId9", "vruiz@kuali.edu", "otherId9", "vruiz", true, "Chemical", "Vincent", "R", "Ruiz", "Male", "Mr.", "", "179 Glacier Road", "Buckland", "MA", "01338"),
    OTHERID10("AFLT", "666660010", "otherId10", "dprice@kuali.edu", "otherId10", "dprice", true, "Chemistry", "Derek", "I", "Price", "Male", "Mr.", "", "156 Groh Street", "Lowell", "MA", "01851");
    private String affiliation;
    private String ssn;
    private String principalId;
    private String principalName;
    private String entityId;
    private String password;
    private boolean active;
    private String dept;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String title;
    private String suffix;
    private String street;
    private String city;
    private String state;
    private String zip;

    private PersonEnum(String affiliation,
            String ssn,
            String principalId,
            String principalName,
            String entityId,
            String password,
            boolean active,
            String dept,
            String firstName,
            String middleName,
            String lastName,
            String gender,
            String title,
            String suffix,
            String street,
            String city,
            String state,
            String zip) {
        this.affiliation = affiliation;
        this.ssn = ssn;
        this.principalId = principalId;
        this.principalName = principalName;
        this.entityId = entityId;
        this.password = password;
        this.active = active;
        this.dept = dept;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.title = title;
        this.suffix = suffix;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFormattedName() {
        StringBuilder bldr = new StringBuilder();
        bldr.append(firstName);
        if (middleName != null) {
            bldr.append(" ");
            bldr.append(middleName);
        }
        bldr.append(" ");
        bldr.append(lastName);
        if (this.suffix != null) {
            bldr.append(" ");
            bldr.append(suffix);
        }
        return bldr.toString();
    }
}


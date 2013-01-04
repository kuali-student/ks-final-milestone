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
package org.kuali.student.kim.identity.mock;

/**
 * @author nwright
 */
public enum PersonEnum {

    STAFF1("STAFF", "888880001", "staff1", "rcurry@kuali.edu", "staff1", "rcurry", true, "Registrar", "Robin", "G", "Curry", "Female", "Ms.", "", "214 East 42nd Place", "Raynham", "MA", "02767"),
    STAFF2("STAFF", "888880002", "staff2", "wmendez@kuali.edu", "staff2", "wmendez", true, "Registrar", "Willie", "O", "Mendez", "Female", "Ms.", "", "232 Ridge Top Circle", "Lexington", "MA", "02277"),
    STAFF3("STAFF", "888880003", "staff3", "msherman@kuali.edu", "staff3", "msherman", true, "FinancialAid", "Melody", "A", "Sherman", "Female", "Ms.", "", "205 Spruce Lane", "New Bedford", "MA", "02742"),
    STAFF4("STAFF", "888880004", "staff4", "eharper@kuali.edu", "staff4", "eharper", true, "StudentAccounts", "Edwin", "K", "Harper", "Male", "Mr.", "", "68 Morino Street", "Boston", "MA", "02117"),
    STAFF5("STAFF", "888880005", "staff5", "bbryant@kuali.edu", "staff5", "bbryant", true, "Advising", "Billy", "K", "Bryant", "Male", "Dr.", "", "122 West 69th Court", "East Princeton", "MA", "01517"),
    STAFF6("STAFF", "888880006", "staff6", "tneal@kuali.edu", "staff6", "tneal", true, "Admissions", "Tamara", "I", "Neal", "Female", "Ms.", "", "239 Lockheed Avenue", "South Egremont", "MA", "01258"),
    STAFF7("STAFF", "888880007", "staff7", "lfields@kuali.edu", "staff7", "lfields", true, "Administration", "Lorraine", "H", "Fields", "Female", "Ms.", "", "263 Brenner Circle", "Bedford", "MA", "01730"),
    STAFF8("STAFF", "888880008", "staff8", "ljenkins@kuali.edu", "staff8", "ljenkins", true, "English", "Lloyd", "D", "Jenkins", "Male", "Mr.", "", "15 West 34th Avenue", "Westport", "MA", "02790"),
    STAFF9("STAFF", "888880009", "staff9", "npotter@kuali.edu", "staff9", "npotter", true, "FineArts", "Natasha", "I", "Potter", "Female", "Ms.", "", "260 Tom White Circle", "Drury", "MA", "01343"),
    STAFF10("STAFF", "888880010", "staff10", "afrost@kuali.edu", "staff10", "afrost", true, "Advising", "Arthur", "X", "Frost", "Male", "Dr.", "III", "43 Harp Circle", "North Carver", "MA", "02355"),
    STAFF11("STAFF", "888880011", "staff11", "plawson@kuali.edu", "staff11", "plawson", true, "AfricanAmericanStudies", "Paula", "S", "Lawson", "Female", "Ms.", "", "54 Wright Street", "North Dighton", "MA", "02764"),
    STAFF12("STAFF", "888880012", "staff12", "jholt@kuali.edu", "staff12", "jholt", true, "AnimalAvianSciences", "Jessica", "E", "Holt", "Female", "Ms.", "", "267 East 41st Court", "North Pembroke", "MA", "02358"),
    STAFF13("STAFF", "888880013", "staff13", "jstewart@kuali.edu", "staff13", "jstewart", true, "Athletics", "Joann", "M", "Stewart", "Female", "Ms.", "", "171 Wingate Circle", "Newton Lower Falls", "MA", "02162"),
    STAFF14("STAFF", "888880014", "staff14", "bbaker@kuali.edu", "staff14", "bbaker", true, "Biochemistry", "Bradley", "E", "Baker", "Male", "Mr.", "Jr.", "90 Chilkat Court", "Amherst", "MA", "01002"),
    STAFF15("STAFF", "888880015", "staff15", "wross@kuali.edu", "staff15", "wross", true, "Registrar", "Wendy", "M", "Ross", "Female", "Ms.", "", "127 West 83Rd Avenue", "Dalton", "MA", "01226"),
    STAFF16("STAFF", "888880016", "staff16", "ksullivan@kuali.edu", "staff16", "ksullivan", true, "FinancialAid", "Kim", "B", "Sullivan", "Female", "Ms.", "", "221 Brooks Road", "Sunderland", "MA", "01375"),
    STAFF17("STAFF", "888880017", "staff17", "hroman@kuali.edu", "staff17", "hroman", true, "StudentAccounts", "Harry", "P", "Roman", "Male", "Mr.", "", "265 Hillhaven Circle", "East Dennis", "MA", "02641"),
    STAFF18("STAFF", "888880018", "staff18", "mgallegos@kuali.edu", "staff18", "mgallegos", true, "Advising", "Misty", "N", "Gallegos", "Female", "Ms.", "", "213 Sarichef Loop", "Orange", "MA", "01355"),
    STAFF19("STAFF", "888880019", "staff19", "rpeterson@kuali.edu", "staff19", "rpeterson", true, "Chemistry", "Ramon", "B", "Peterson", "Male", "Mr.", "", "205 Whitefish Circle", "Worcester", "MA", "01603"),
    STAFF20("STAFF", "888880020", "staff20", "bfisher@kuali.edu", "staff20", "bfisher", true, "Civil", "Beverly", "B", "Fisher", "Female", "Ms.", "", "286 Randamar Circle", "Tyngsboro", "MA", "01879"),
    STAFF21("STAFF", "888880021", "staff21", "sfrazier@kuali.edu", "staff21", "sfrazier", true, "CompSci", "Stephen", "H", "Frazier", "Male", "Mr.", "", "181 Wesleyan Drive", "South Weymouth", "MA", "02190"),
    STAFF22("STAFF", "888880022", "staff22", "jchavez@kuali.edu", "staff22", "jchavez", true, "CrimCrimJus", "Julia", "G", "Chavez", "Female", "Ms.", "", "121 Caribou Avenue", "Boston", "MA", "02103"),
    STAFF23("STAFF", "888880023", "staff23", "fpearson@kuali.edu", "staff23", "fpearson", true, "Economics", "Floyd", "J", "Pearson", "Male", "Mr.", "", "40 Kenny Place", "Housatonic", "MA", "01236"),
    STAFF24("STAFF", "888880024", "staff24", "fmorrow@kuali.edu", "staff24", "fmorrow", true, "English", "Frank", "F", "Morrow", "Male", "Mr.", "IIV", "64 Woodcliff Court", "Centerville", "MA", "02636"),
    STAFF25("STAFF", "888880025", "staff25", "eperez@kuali.edu", "staff25", "eperez", true, "FineArts", "Esther", "G", "Perez", "Female", "Ms.", "", "257 Anchor Park Circle", "Wellfleet", "MA", "02667"),
    STAFF26("STAFF", "888880026", "staff26", "fcampbell@kuali.edu", "staff26", "fcampbell", true, "French", "Frederick", "R", "Campbell", "Male", "Mr.", "", "29 Chaimi Loop", "Rowe", "MA", "01367"),
    STAFF27("STAFF", "888880027", "staff27", "kberry@kuali.edu", "staff27", "kberry", true, "Geography", "Kelli", "U", "Berry", "Female", "Ms.", "", "100 Beaufort Way", "Worcester", "MA", "01654"),
    STAFF28("STAFF", "888880028", "staff28", "wadams@kuali.edu", "staff28", "wadams", true, "Geology", "Wayne", "V", "Adams", "Male", "Mr.", "", "143 Doggie Avenue", "Dunstable", "MA", "01827"),
    STAFF29("STAFF", "888880029", "staff29", "rwalters@kuali.edu", "staff29", "rwalters", true, "History", "Rodney", "Q", "Walters", "Male", "Mr.", "", "176 Ridge Park Drive", "Allston", "MA", "02134"),
    STAFF30("STAFF", "888880030", "staff30", "dhouston@kuali.edu", "staff30", "dhouston", true, "Linguistics", "Deanna", "O", "Houston", "Female", "Ms.", "", "259 Brooke Drive", "Montague", "MA", "01351"),
    STAFF31("STAFF", "888880031", "staff31", "bcollins@kuali.edu", "staff31", "bcollins", true, "Mathematics", "Brandon", "Y", "Collins", "Male", "Mr.", "", "217 Nystrom Street", "Forestdale", "MA", "02644"),
    STAFF32("STAFF", "888880032", "staff32", "tbarnes@kuali.edu", "staff32", "tbarnes", true, "Mechanical", "Tina", "Q", "Barnes", "Female", "Dr.", "", "216 Loganberry Street", "Boston", "MA", "02203"),
    STAFF33("STAFF", "888880033", "staff33", "freed@kuali.edu", "staff33", "freed", true, "Music", "Frances", "P", "Reed", "Female", "Ms.", "", "138 Hanley Circle", "Boston", "MA", "02183"),
    STAFF34("STAFF", "888880034", "staff34", "aluna@kuali.edu", "staff34", "aluna", true, "Philosophy", "Alan", "H", "Luna", "Male", "Mr.", "", "150 Kogru Place", "Boston", "MA", "02101"),
    STAFF35("STAFF", "888880035", "staff35", "kcaldwell@kuali.edu", "staff35", "kcaldwell", true, "Physics", "Kristin", "H", "Caldwell", "Female", "Ms.", "", "221 Headlands Circle", "Worcester", "MA", "01612"),
    STAFF36("STAFF", "888880036", "staff36", "jgraham@kuali.edu", "staff36", "jgraham", true, "PolySci", "Jessie", "H", "Graham", "Female", "Ms.", "", "158 California Creek Way", "Worcester", "MA", "01607"),
    STAFF37("STAFF", "888880037", "staff37", "rknight@kuali.edu", "staff37", "rknight", true, "Psychology", "Ray", "K", "Knight", "Male", "Mr.", "", "261 East 101st Avenue", "Nonantum", "MA", "02195"),
    STAFF38("STAFF", "888880038", "staff38", "sray@kuali.edu", "staff38", "sray", true, "Sociology", "Suzanne", "S", "Ray", "Female", "Ms.", "", "164 Deerfield Drive", "Oakham", "MA", "01068"),
    STAFF39("STAFF", "888880039", "staff39", "cstevenson@kuali.edu", "staff39", "cstevenson", true, "FineArts", "Carl", "S", "Stevenson", "Male", "Mr.", "", "295 Dale Street", "New Bedford", "MA", "02740"),
    STAFF40("STAFF", "888880040", "staff40", "pbowman@kuali.edu", "staff40", "pbowman", true, "French", "Pedro", "L", "Bowman", "Male", "Dr.", "", "176 Wanigan Street", "Berlin", "MA", "01503"),
    STAFF41("STAFF", "888880041", "staff41", "ndunn@kuali.edu", "staff41", "ndunn", true, "Geography", "Natalie", "L", "Dunn", "Female", "Ms.", "", "32 Lynx Way", "Jamaica Plain", "MA", "02130"),
    STAFF42("STAFF", "888880042", "staff42", "sjohns@kuali.edu", "staff42", "sjohns", true, "Geology", "Shawna", "Q", "Johns", "Female", "Ms.", "", "52 Whiteney Circle", "Ayer", "MA", "01432"),
    STAFF43("STAFF", "888880043", "staff43", "bramsey@kuali.edu", "staff43", "bramsey", true, "History", "Billy", "V", "Ramsey", "Male", "Mr.", "", "214 West 73Rd Circle", "Wrentham", "MA", "02093"),
    STAFF44("STAFF", "888880044", "staff44", "ebailey@kuali.edu", "staff44", "ebailey", true, "Linguistics", "Edgar", "Y", "Bailey", "Male", "Mr.", "", "187 Heide Drive", "Windsor", "MA", "01270"),
    STAFF45("STAFF", "888880045", "staff45", "mgreer@kuali.edu", "staff45", "mgreer", true, "Mathematics", "Margie", "H", "Greer", "Female", "Ms.", "", "78 Banff Circle", "Erving", "MA", "01344"),
    STAFF46("STAFF", "888880046", "staff46", "eelliott@kuali.edu", "staff46", "eelliott", true, "Mechanical", "Ella", "D", "Elliott", "Female", "Ms.", "", "35 Hillside Drive", "Spencer", "MA", "01562"),
    STAFF47("STAFF", "888880047", "staff47", "mfleming@kuali.edu", "staff47", "mfleming", true, "Music", "Melinda", "Q", "Fleming", "Female", "Ms.", "", "113 East 18th Circle", "Savoy", "MA", "01256"),
    STAFF48("STAFF", "888880048", "staff48", "ljohnson@kuali.edu", "staff48", "ljohnson", true, "Philosophy", "Lisa", "R", "Johnson", "Female", "Ms.", "", "201 Starflower Circle", "Conway", "MA", "01341"),
    STAFF49("STAFF", "888880049", "staff49", "msaunders@kuali.edu", "staff49", "msaunders", true, "Physics", "Molly", "K", "Saunders", "Female", "Ms.", "", "268 Arlington Drive North", "Shelburne Falls", "MA", "01370"),
    STAFF50("STAFF", "888880050", "staff50", "dyates@kuali.edu", "staff50", "dyates", true, "PolySci", "Derek", "J", "Yates", "Male", "Mr.", "", "290 Earl Court", "Westborough", "MA", "01580"),
    STAFF51("STAFF", "888880051", "staff51", "fwood@kuali.edu", "staff51", "fwood", true, "Psychology", "Florence", "P", "Wood", "Female", "Ms.", "", "241 East 10th Avenue", "Berkley", "MA", "02779"),
    STAFF52("STAFF", "888880052", "staff52", "scole@kuali.edu", "staff52", "scole", true, "Athletics", "Shirley", "R", "Cole", "Female", "Ms.", "", "1 Thomas Road", "Concord", "MA", "01742"),
    STAFF53("STAFF", "888880053", "staff53", "kstokes@kuali.edu", "staff53", "kstokes", true, "Biochemistry", "Keith", "T", "Stokes", "Male", "Mr.", "", "255 Citadel Lane", "Gardner", "MA", "01441"),
    STAFF54("STAFF", "888880054", "staff54", "tsimon@kuali.edu", "staff54", "tsimon", true, "Biology", "Toni", "K", "Simon", "Female", "Ms.", "", "95 Camelot Circle", "Stockbridge", "MA", "01262"),
    STAFF55("STAFF", "888880055", "staff55", "triley@kuali.edu", "staff55", "triley", true, "Botany", "Tracey", "X", "Riley", "Male", "Mr.", "", "36 Moody Street", "Fall River", "MA", "02722"),
    STAFF56("STAFF", "888880056", "staff56", "nkramer@kuali.edu", "staff56", "nkramer", true, "CareerServices", "Nora", "A", "Kramer", "Female", "Ms.", "", "245 Prominence Pointe Drive", "Boston", "MA", "02104"),
    STAFF57("STAFF", "888880057", "staff57", "fgardner@kuali.edu", "staff57", "fgardner", true, "Chemical", "Frank", "A", "Gardner", "Male", "Mr.", "", "265 Bitterroot Circle", "Cuttyhunk", "MA", "02713"),
    STAFF58("STAFF", "888880058", "staff58", "jharris@kuali.edu", "staff58", "jharris", true, "Chemistry", "James", "R", "Harris", "Male", "Mr.", "", "120 Jack Street", "Franklin", "MA", "02038"),
    STAFF59("STAFF", "888880059", "staff59", "kpatterson@kuali.edu", "staff59", "kpatterson", true, "Civil", "Kathleen", "U", "Patterson", "Female", "Ms.", "", "40 Ruth Street", "South Hadley", "MA", "01075"),
    STAFF60("STAFF", "888880060", "staff60", "clowe@kuali.edu", "staff60", "clowe", true, "CompSci", "Claudia", "R", "Lowe", "Female", "Ms.", "", "160 Chasewood Lane", "Pocasset", "MA", "02559"),
    STAFF61("STAFF", "888880061", "staff61", "rfrank@kuali.edu", "staff61", "rfrank", true, "French", "Robert", "E", "Frank", "Male", "Mr.", "", "128 Glenview Street", "Chilmark", "MA", "02535"),
    STAFF62("STAFF", "888880062", "staff62", "smartinez@kuali.edu", "staff62", "smartinez", true, "French", "Sharon", "Y", "Martinez", "Female", "Ms.", "", "76 Briny Circle", "Newburyport", "MA", "01950"),
    FACULTY1("FCLTY", "999990001", "faculty1", "pmiles@kuali.edu", "faculty1", "pmiles", true, "Advising", "Perry", "O", "Miles", "Male", "Mr.", "", "87 Bryant Circle", "Lynn", "MA", "01904"),
    FACULTY2("FCLTY", "999990002", "faculty2", "drobinson@kuali.edu", "faculty2", "drobinson", true, "AfricanAmericanStudies", "Daniel", "B", "Robinson", "Male", "Mr.", "", "198 Aircraft Drive", "Great Barrington", "MA", "01230"),
    FACULTY3("FCLTY", "999990003", "faculty3", "chaynes@kuali.edu", "faculty3", "chaynes", true, "AnimalAvianSciences", "Christopher", "R", "Haynes", "Male", "Mr.", "", "162 Spruce Street", "Nutting Lake", "MA", "01865"),
    FACULTY4("FCLTY", "999990004", "faculty4", "hhicks@kuali.edu", "faculty4", "hhicks", true, "Athletics", "Hector", "U", "Hicks", "Male", "Mr.", "", "69 North Boniface Parkway", "Hingham", "MA", "02043"),
    FACULTY5("FCLTY", "999990005", "faculty5", "mhowell@kuali.edu", "faculty5", "mhowell", true, "Biochemistry", "Matthew", "M", "Howell", "Male", "Dr.", "", "257 Cobblecreek Circle", "West Bridgewater", "MA", "02379"),
    FACULTY6("FCLTY", "999990006", "faculty6", "sjones@kuali.edu", "faculty6", "sjones", true, "Biology", "Samuel", "B", "Jones", "Male", "Mr.", "", "40 Dickson Road", "Rockland", "MA", "02370"),
    FACULTY7("FCLTY", "999990007", "faculty7", "apeterson@kuali.edu", "faculty7", "apeterson", true, "Botany", "Angela", "D", "Peterson", "Female", "Ms.", "", "242 Palos Verdes Circle", "South Carver", "MA", "02366"),
    FACULTY8("FCLTY", "999990008", "faculty8", "rtate@kuali.edu", "faculty8", "rtate", true, "CareerServices", "Roxanne", "S", "Tate", "Female", "Ms.", "", "165 Timberlane Circle", "Springfield", "MA", "01106"),
    FACULTY9("FCLTY", "999990009", "faculty9", "jfinley@kuali.edu", "faculty9", "jfinley", true, "Chemical", "Jesse", "N", "Finley", "Male", "Mr.", "", "246 Potter Crest Circle", "Boston", "MA", "02294"),
    FACULTY10("FCLTY", "999990010", "faculty10", "dmorgan@kuali.edu", "faculty10", "dmorgan", true, "Chemistry", "Dennis", "V", "Morgan", "Male", "Mr.", "", "18 Glacier Bay Circle", "West Boxford", "MA", "01885"),
    FACULTY11("FCLTY", "999990011", "faculty11", "kmccoy@kuali.edu", "faculty11", "kmccoy", true, "Civil", "Karl", "R", "McCoy", "Male", "Mr.", "", "206 Constitution Street", "Cataumet", "MA", "02534"),
    FACULTY12("FCLTY", "999990012", "faculty12", "vkelley@kuali.edu", "faculty12", "vkelley", true, "CompSci", "Vickie", "S", "Kelley", "Female", "Ms.", "", "108 Seville Park Circle", "Northborough", "MA", "01532"),
    FACULTY13("FCLTY", "999990013", "faculty13", "cvasquez@kuali.edu", "faculty13", "cvasquez", true, "CrimCrimJus", "Cecilia", "G", "Vasquez", "Female", "Ms.", "", "198 West 70th Circle", "Boston", "MA", "02217"),
    FACULTY14("FCLTY", "999990014", "faculty14", "oterry@kuali.edu", "faculty14", "oterry", true, "Economics", "Opal", "I", "Terry", "Female", "Ms.", "", "211 Travis Street", "North Scituate", "MA", "02060"),
    FACULTY15("FCLTY", "999990015", "faculty15", "rstephens@kuali.edu", "faculty15", "rstephens", true, "English", "Regina", "Q", "Stephens", "Female", "Ms.", "", "205 Sarabella Lane", "Essex", "MA", "01929"),
    FACULTY16("FCLTY", "999990016", "faculty16", "lwall@kuali.edu", "faculty16", "lwall", true, "FineArts", "Lindsay", "I", "Wall", "Female", "Ms.", "", "132 Laurel Street", "Richmond", "MA", "01254"),
    FACULTY17("FCLTY", "999990017", "faculty17", "eballard@kuali.edu", "faculty17", "eballard", true, "French", "Eunice", "O", "Ballard", "Female", "Ms.", "", "89 East 16th Avenue", "Ashley Falls", "MA", "01222"),
    FACULTY18("FCLTY", "999990018", "faculty18", "lweaver@kuali.edu", "faculty18", "lweaver", true, "Geography", "Lucille", "R", "Weaver", "Female", "Ms.", "", "39 Shady Bay Circle", "North Brookfield", "MA", "01535"),
    FACULTY19("FCLTY", "999990019", "faculty19", "jphillips@kuali.edu", "faculty19", "jphillips", true, "Geology", "Janice", "G", "Phillips", "Female", "Ms.", "", "28 Minnesota Drive", "Woburn", "MA", "01814"),
    FACULTY20("FCLTY", "999990020", "faculty20", "crice@kuali.edu", "faculty20", "crice", true, "History", "Charlotte", "A", "Rice", "Female", "Ms.", "", "43 Rebischke Lane", "Boston", "MA", "02108"),
    FACULTY21("FCLTY", "999990021", "faculty21", "gporter@kuali.edu", "faculty21", "gporter", true, "Linguistics", "Glenda", "Q", "Porter", "Female", "Ms.", "", "266 Butte Circle", "Arlington Heights", "MA", "02175"),
    FACULTY22("FCLTY", "999990022", "faculty22", "egray@kuali.edu", "faculty22", "egray", true, "Mathematics", "Eric", "M", "Gray", "Male", "Mr.", "", "97 Summerset Drive", "Cambridge", "MA", "02142"),
    FACULTY23("FCLTY", "999990023", "faculty23", "ddennis@kuali.edu", "faculty23", "ddennis", true, "Mechanical", "Doris", "E", "Dennis", "Female", "Ms.", "", "49 Alpine Court", "Palmer", "MA", "01069"),
    FACULTY24("FCLTY", "999990024", "faculty24", "mlong@kuali.edu", "faculty24", "mlong", true, "Music", "Michelle", "Q", "Long", "Female", "Ms.", "", "142 Sundi Way", "Boston", "MA", "02296"),
    FACULTY25("FCLTY", "999990025", "faculty25", "dhorton@kuali.edu", "faculty25", "dhorton", true, "Philosophy", "Dolores", "Q", "Horton", "Female", "Ms.", "", "151 East Loop Road", "Edgartown", "MA", "02539"),
    FACULTY26("FCLTY", "999990026", "faculty26", "nevans@kuali.edu", "faculty26", "nevans", true, "Physics", "Norma", "P", "Evans", "Female", "Ms.", "", "143 Laurence Court", "Cohasset", "MA", "02025"),
    FACULTY27("FCLTY", "999990027", "faculty27", "mwilliamson@kuali.edu", "faculty27", "mwilliamson", true, "PolySci", "Miguel", "H", "Williamson", "Male", "Mr.", "", "131 Timberwolf Circle", "Lenox", "MA", "01240"),
    FACULTY28("FCLTY", "999990028", "faculty28", "gjennings@kuali.edu", "faculty28", "gjennings", true, "Psychology", "Gerald", "O", "Jennings", "Male", "Mr.", "", "20 J-K Lane", "Cambridge", "MA", "02140"),
    FACULTY29("FCLTY", "999990029", "faculty29", "bmeyer@kuali.edu", "faculty29", "bmeyer", true, "Sociology", "Betsy", "Q", "Meyer", "Female", "Ms.", "", "220 Camelot Drive", "West Barnstable", "MA", "02668"),
    FACULTY30("FCLTY", "999990030", "faculty30", "mwells@kuali.edu", "faculty30", "mwells", true, "AfricanAmericanStudies", "Manuel", "T", "Wells", "Male", "Mr.", "", "91 Emerald Street", "Millis", "MA", "02054"),
    FACULTY31("FCLTY", "999990031", "faculty31", "mmcclain@kuali.edu", "faculty31", "mmcclain", true, "AnimalAvianSciences", "Madeline", "M", "McClain", "Female", "Ms.", "", "97 East 66th Avenue", "Roxbury", "MA", "02120"),
    FACULTY32("FCLTY", "999990032", "faculty32", "pmorris@kuali.edu", "faculty32", "pmorris", true, "Athletics", "Phillip", "P", "Morris", "Male", "Mr.", "", "16 Cutlass Circle", "Somerville", "MA", "02145"),
    FACULTY33("FCLTY", "999990033", "faculty33", "jhancock@kuali.edu", "faculty33", "jhancock", true, "Biochemistry", "James", "D", "Hancock", "Male", "Mr.", "", "239 Wren Circle", "Becket", "MA", "01223"),
    FACULTY34("FCLTY", "999990034", "faculty34", "jrivas@kuali.edu", "faculty34", "jrivas", true, "Biology", "Jeff", "R", "Rivas", "Male", "Mr.", "", "78 Wood River Way", "Cambridge", "MA", "02141"),
    FACULTY35("FCLTY", "999990035", "faculty35", "tbaxter@kuali.edu", "faculty35", "tbaxter", true, "Botany", "Tara", "V", "Baxter", "Female", "Ms.", "", "74 Winners Circle", "North Adams", "MA", "01247"),
    FACULTY36("FCLTY", "999990036", "faculty36", "ewood@kuali.edu", "faculty36", "ewood", true, "CareerServices", "Elmer", "Q", "Wood", "Male", "Mr.", "", "60 Kensington Circle", "Groton", "MA", "01471"),
    FACULTY37("FCLTY", "999990037", "faculty37", "ssutton@kuali.edu", "faculty37", "ssutton", true, "Chemical", "Steve", "G", "Sutton", "Male", "Mr.", "", "42 Teri Circle", "Worcester", "MA", "01600"),
    FACULTY38("FCLTY", "999990038", "faculty38", "charvey@kuali.edu", "faculty38", "charvey", true, "Chemistry", "Carmen", "M", "Harvey", "Female", "Ms.", "", "21 Trapline Circle", "Brant Rock", "MA", "02020"),
    FACULTY39("FCLTY", "999990039", "faculty39", "cgriffith@kuali.edu", "faculty39", "cgriffith", true, "Civil", "Candace", "V", "Griffith", "Female", "Ms.", "", "54 Chandelle Circle", "Springfield", "MA", "01118"),
    FACULTY40("FCLTY", "999990040", "faculty40", "crivera@kuali.edu", "faculty40", "crivera", true, "CompSci", "Christina", "V", "Rivera", "Female", "Ms.", "", "279 Seal Point Circle", "East Otis", "MA", "01029"),
    FACULTY41("FCLTY", "999990041", "faculty41", "rstewart@kuali.edu", "faculty41", "rstewart", true, "CrimCrimJus", "Ryan", "I", "Stewart", "Male", "Mr.", "", "157 Dee Circle", "Marlborough", "MA", "01752"),
    FACULTY42("FCLTY", "999990042", "faculty42", "dbarrett@kuali.edu", "faculty42", "dbarrett", true, "Economics", "Debra", "Q", "Barrett", "Female", "Ms.", "", "261 Norene Street", "Dover", "MA", "02030"),
    FACULTY43("FCLTY", "999990043", "faculty43", "dgray@kuali.edu", "faculty43", "dgray", true, "English", "Dianna", "A", "Gray", "Female", "Ms.", "", "85 Duben Avenue", "Onset", "MA", "02558"),
    FACULTY44("FCLTY", "999990044", "faculty44", "swillis@kuali.edu", "faculty44", "swillis", true, "FineArts", "Sophia", "O", "Willis", "Female", "Ms.", "", "39 Telder Street", "Northfield", "MA", "01360"),
    FACULTY45("FCLTY", "999990045", "faculty45", "jfranklin@kuali.edu", "faculty45", "jfranklin", true, "French", "Jamie", "E", "Franklin", "Female", "Ms.", "", "206 Talisman Road", "Worcester", "MA", "01653"),
    FACULTY46("FCLTY", "999990046", "faculty46", "lfernandez@kuali.edu", "faculty46", "lfernandez", true, "Geography", "Lawrence", "Z", "Fernandez", "Male", "Mr.", "", "67 Tamworth Circle", "Scituate", "MA", "02066"),
    FACULTY47("FCLTY", "999990047", "faculty47", "grios@kuali.edu", "faculty47", "grios", true, "Geology", "Glen", "S", "Rios", "Male", "Mr.", "", "159 Goose Berry Place", "Lawrence", "MA", "01841"),
    FACULTY48("FCLTY", "999990048", "faculty48", "bfowler@kuali.edu", "faculty48", "bfowler", true, "History", "Bobby", "H", "Fowler", "Male", "Mr.", "", "89 Eshamy Bay Drive", "Framingham", "MA", "01701"),
    FACULTY49("FCLTY", "999990049", "faculty49", "rherrera@kuali.edu", "faculty49", "rherrera", true, "Linguistics", "Ruby", "Q", "Herrera", "Female", "Ms.", "", "216 Trisha Avenue", "Boston", "MA", "02201"),
    FACULTY50("FCLTY", "999990050", "faculty50", "njackson@kuali.edu", "faculty50", "njackson", true, "Mathematics", "Nancy", "Q", "Jackson", "Female", "Ms.", "", "96 The Sun Loft Drive", "Middleboro", "MA", "02349"),
    FACULTY51("FCLTY", "999990051", "faculty51", "rdavIdson@kuali.edu", "faculty51", "rdavIdson", true, "Mechanical", "Raymond", "M", "DavIdson", "Male", "Mr.", "", "273 Nash Street", "Middlesex Essex Gmf", "MA", "01889"),
    FACULTY52("FCLTY", "999990052", "faculty52", "slane@kuali.edu", "faculty52", "slane", true, "Music", "Shane", "D", "Lane", "Male", "Mr.", "", "37 Colchis Street", "West Medford", "MA", "02156"),
    FACULTY53("FCLTY", "999990053", "faculty53", "rmueller@kuali.edu", "faculty53", "rmueller", true, "Philosophy", "Raquel", "T", "Mueller", "Female", "Ms.", "", "237 Young Drive", "East Taunton", "MA", "02718"),
    FACULTY54("FCLTY", "999990054", "faculty54", "rnunez@kuali.edu", "faculty54", "rnunez", true, "Physics", "Randall", "M", "Nunez", "Male", "Mr.", "", "146 Atkinson Circle", "Hadley", "MA", "01035"),
    FACULTY55("FCLTY", "999990055", "faculty55", "khill@kuali.edu", "faculty55", "khill", true, "PolySci", "Karen", "I", "Hill", "Female", "Ms.", "", "2 Fisher Drive", "Haverhill", "MA", "01832"),
    FACULTY56("FCLTY", "999990056", "faculty56", "hvargas@kuali.edu", "faculty56", "hvargas", true, "Psychology", "Holly", "X", "Vargas", "Female", "Ms.", "", "54 Madigan Circle", "Brewster", "MA", "02631"),
    FACULTY57("FCLTY", "999990057", "faculty57", "kalvarez@kuali.edu", "faculty57", "kalvarez", true, "Sociology", "Kristine", "C", "Alvarez", "Female", "Ms.", "", "215 Brittany Place", "Athol", "MA", "01368"),
    FACULTY58("FCLTY", "999990058", "faculty58", "jbass@kuali.edu", "faculty58", "jbass", true, "AfricanAmericanStudies", "Jacquelyn", "D", "Bass", "Female", "Ms.", "", "91 Klutina Circle", "Dorchester", "MA", "02122"),
    FACULTY59("FCLTY", "999990059", "faculty59", "bwagner@kuali.edu", "faculty59", "bwagner", true, "AnimalAvianSciences", "Bradley", "G", "Wagner", "Male", "Mr.", "", "116 Amberwood Circle", "West Harwich", "MA", "02671"),
    FACULTY60("FCLTY", "999990060", "faculty60", "mjimenez@kuali.edu", "faculty60", "mjimenez", true, "Athletics", "Mike", "D", "Jimenez", "Male", "Mr.", "", "191 West 38th Place", "Revere", "MA", "02151"),
    STUDENT1("STDNT", "777770001", "student1", "kstone@kuali.edu", "student1", "kstone", true, "Biochemistry", "Kara", "Q", "Stone", "Female", "Ms.", "", "11 Walrus Circle", "West Newton", "MA", "02165"),
    STUDENT2("STDNT", "777770002", "student2", "bharris@kuali.edu", "student2", "bharris", true, "Biology", "Barbara", "A", "Harris", "Female", "Ms.", "", "35 Penny Circle", "Boston", "MA", "02299"),
    STUDENT3("STDNT", "777770003", "student3", "criddle@kuali.edu", "student3", "criddle", true, "Botany", "Clifford", "I", "Riddle", "Male", "Mr.", "", "59 Atlantis Avenue", "Lynnfield", "MA", "01940"),
    STUDENT4("STDNT", "777770004", "student4", "nwelch@kuali.edu", "student4", "nwelch", true, "CareerServices", "Nina", "X", "Welch", "Female", "Ms.", "", "66 Gina Circle", "East Boston", "MA", "02128"),
    STUDENT5("STDNT", "777770005", "student5", "bmartin@kuali.edu", "student5", "bmartin", true, "Chemical", "Betty", "J", "Martin", "Female", "Ms.", "", "141 East 48th Avenue", "Boston", "MA", "02233"),
    STUDENT6("STDNT", "777770006", "student6", "nmcdonald@kuali.edu", "student6", "nmcdonald", true, "Chemistry", "Nellie", "N", "McDonald", "Female", "Ms.", "", "144 Scenic Drive", "Boston", "MA", "02295"),
    STUDENT7("STDNT", "777770007", "student7", "kthompson@kuali.edu", "student7", "kthompson", true, "Civil", "Kimberly", "K", "Thompson", "Female", "Ms.", "", "163 Ziemlak Circle", "Lee", "MA", "01264"),
    STUDENT8("STDNT", "777770008", "student8", "ahopkins@kuali.edu", "student8", "ahopkins", true, "CompSci", "Amber", "D", "Hopkins", "Female", "Ms.", "", "178 Johnny Drive", "Boylston", "MA", "01505"),
    STUDENT9("STDNT", "777770009", "student9", "jmanning@kuali.edu", "student9", "jmanning", true, "CrimCrimJus", "Johnny", "F", "Manning", "Male", "Mr.", "", "85 Rosella Street", "Brockton", "MA", "02401"),
    STUDENT10("STDNT", "", "student10", "epittman@kuali.edu", "student10", "epittman", true, "Economics", "Eddie", "S", "Pittman", "Male", "Mr.", "", "178 Hazen Lane", "North Easton", "MA", "02356"),
    STUDENT11("STDNT", "777770011", "student11", "tburton@kuali.edu", "student11", "tburton", true, "English", "Tracy", "P", "Burton", "Female", "Ms.", "", "154 North Bunn Street", "Winchester", "MA", "01890"),
    STUDENT12("STDNT", "777770012", "student12", "gblake@kuali.edu", "student12", "gblake", true, "FineArts", "Gertrude", "S", "Blake", "Female", "Ms.", "", "141 Shadetree Circle", "Hanover", "MA", "02339"),
    STUDENT13("STDNT", "777770013", "student13", "dmcclure@kuali.edu", "student13", "dmcclure", true, "French", "Donald", "S", "McClure", "Male", "Mr.", "", "35 M Court", "Lowell", "MA", "01853"),
    STUDENT14("STDNT", "777770014", "student14", "jprince@kuali.edu", "student14", "jprince", true, "Geography", "Joel", "K", "Prince", "Male", "Mr.", "", "197 Tedrow Drive", "North Attleboro", "MA", "02763"),
    STUDENT15("STDNT", "777770015", "student15", "sgonzales@kuali.edu", "student15", "sgonzales", true, "Geology", "Samantha", "A", "Gonzales", "Female", "Ms.", "", "46 Hampton Circle", "Chelsea", "MA", "02150"),
    STUDENT16("STDNT", "777770016", "student16", "frussell@kuali.edu", "student16", "frussell", true, "History", "Francisco", "S", "Russell", "Male", "Mr.", "", "242 Klingler Street", "Sturbridge", "MA", "01566"),
    STUDENT17("STDNT", "777770017", "student17", "aingram@kuali.edu", "student17", "aingram", true, "Linguistics", "Austin", "E", "Ingram", "Male", "Mr.", "", "217 Arne Erickson Circle", "Wales", "MA", "01081"),
    STUDENT18("STDNT", "777770018", "student18", "rwhite@kuali.edu", "student18", "rwhite", true, "Mathematics", "Rebecca", "I", "White", "Female", "Ms.", "", "16 Bunker Street", "Webster", "MA", "01570"),
    STUDENT19("STDNT", "777770019", "student19", "mpeters@kuali.edu", "student19", "mpeters", true, "Mechanical", "Mildred", "Q", "Peters", "Female", "Ms.", "", "126 South Windsor Circle", "Canton", "MA", "02021"),
    STUDENT20("STDNT", "777770020", "student20", "tparker@kuali.edu", "student20", "tparker", true, "Music", "Timothy", "O", "Parker", "Male", "Mr.", "", "94 Tonsina Court", "Holliston", "MA", "01746"),
    STUDENT21("STDNT", "777770021", "student21", "tdurham@kuali.edu", "student21", "tdurham", true, "Philosophy", "Thomas", "D", "Durham", "Male", "Mr.", "", "208 Bridle Lane", "Waltham", "MA", "02154"),
    STUDENT22("STDNT", "777770022", "student22", "jmarsh@kuali.edu", "student22", "jmarsh", true, "Physics", "Judith", "S", "Marsh", "Female", "Ms.", "", "28 Scott Street", "West Brookfield", "MA", "01585"),
    STUDENT23("STDNT", "777770023", "student23", "lyoung@kuali.edu", "student23", "lyoung", true, "PolySci", "Larry", "T", "Young", "Male", "Mr.", "", "220 Access Road A Road", "Boston", "MA", "02105"),
    STUDENT24("STDNT", "777770024", "student24", "esims@kuali.edu", "student24", "esims", true, "Psychology", "Erica", "N", "Sims", "Female", "Ms.", "", "213 Windlass Circle", "Medfield", "MA", "02052"),
    STUDENT25("STDNT", "777770025", "student25", "mmunoz@kuali.edu", "student25", "mmunoz", true, "Sociology", "Marvin", "M", "Munoz", "Male", "Mr.", "", "65 Lookout Circle", "South Egremont", "MA", "01252"),
    STUDENT26("STDNT", "777770026", "student26", "kberry@kuali.edu", "student26", "kberry", true, "AfricanAmericanStudies", "Kelly", "E", "Berry", "Male", "Mr.", "", "13 Tarika Avenue", "Williamsburg", "MA", "01096"),
    STUDENT27("STDNT", "777770027", "student27", "jflores@kuali.edu", "student27", "jflores", true, "AnimalAvianSciences", "Jack", "O", "Flores", "Male", "Mr.", "", "279 Jewel Street", "Medford", "MA", "02153"),
    STUDENT28("STDNT", "777770028", "student28", "fhampton@kuali.edu", "student28", "fhampton", true, "Athletics", "Fred", "A", "Hampton", "Male", "Mr.", "", "98 O'Malley Road", "Woburn", "MA", "01801"),
    STUDENT29("STDNT", "777770029", "student29", "khart@kuali.edu", "student29", "khart", true, "Biochemistry", "Kate", "O", "Hart", "Female", "Ms.", "", "160 Hansa Rose Circle", "Beverly", "MA", "01915"),
    STUDENT30("STDNT", "777770030", "student30", "dvaughn@kuali.edu", "student30", "dvaughn", true, "Biology", "Doreen", "Q", "Vaughn", "Female", "Ms.", "", "290 Abbington Court", "Linwood", "MA", "01525"),
    STUDENT31("STDNT", "777770031", "student31", "wbooker@kuali.edu", "student31", "wbooker", true, "Botany", "William", "A", "Booker", "Male", "Mr.", "", "6 Lesmer Circle", "North Falmouth", "MA", "02556"),
    STUDENT32("STDNT", "777770032", "student32", "dwebb@kuali.edu", "student32", "dwebb", true, "CareerServices", "Don", "H", "Webb", "Male", "Mr.", "", "202 Solitude Circle", "East Weymouth", "MA", "02189"),
    STUDENT33("STDNT", "777770033", "student33", "tlewis@kuali.edu", "student33", "tlewis", true, "Chemical", "Travis", "P", "Lewis", "Male", "Mr.", "", "113 Snowmass Circle", "Blandford", "MA", "01008"),
    STUDENT34("STDNT", "777770034", "student34", "hvega@kuali.edu", "student34", "hvega", true, "Chemistry", "Hugh", "Q", "Vega", "Male", "Mr.", "", "207 King Street", "Centerville", "MA", "02634"),
    STUDENT35("STDNT", "777770035", "student35", "aclayton@kuali.edu", "student35", "aclayton", true, "Civil", "Alison", "H", "Clayton", "Female", "Ms.", "", "116 Denise Circle", "Pittsfield", "MA", "01201"),
    STUDENT36("STDNT", "777770036", "student36", "jmorris@kuali.edu", "student36", "jmorris", true, "CompSci", "Joshua", "P", "Morris", "Male", "Mr.", "", "120 Wellington Court", "Manchaug", "MA", "01526"),
    STUDENT37("STDNT", "777770037", "student37", "vsimpson@kuali.edu", "student37", "vsimpson", true, "CrimCrimJus", "Victoria", "D", "Simpson", "Female", "Ms.", "", "165 West Dowling Road", "Sandwich", "MA", "02563"),
    STUDENT38("STDNT", "777770038", "student38", "smckinney@kuali.edu", "student38", "smckinney", true, "Economics", "Stella", "B", "McKinney", "Female", "Ms.", "", "258 Kelly Maureen Circle", "North Attleboro", "MA", "02762"),
    STUDENT39("STDNT", "777770039", "student39", "tporter@kuali.edu", "student39", "tporter", true, "English", "Todd", "Y", "Porter", "Male", "Mr.", "", "93 Buena Vista Drive", "Boston", "MA", "02116"),
    STUDENT40("STDNT", "", "student40", "awilliams@kuali.edu", "student40", "awilliams", true, "FineArts", "Aaron", "V", "Williams", "Male", "Mr.", "", "129 West 94th Court", "Danvers", "MA", "01923"),
    STUDENT41("STDNT", "777770041", "student41", "mgordon@kuali.edu", "student41", "mgordon", true, "French", "Megan", "A", "Gordon", "Female", "Ms.", "", "136 Meadow Wood Circle", "Bolton", "MA", "01740"),
    STUDENT42("STDNT", "777770042", "student42", "kwoodard@kuali.edu", "student42", "kwoodard", true, "Geography", "Kay", "V", "Woodard", "Female", "Ms.", "", "183 Crete Street", "Newburyport", "MA", "01951"),
    STUDENT43("STDNT", "777770043", "student43", "mwinters@kuali.edu", "student43", "mwinters", true, "Geology", "Micheal", "A", "Winters", "Male", "Mr.", "", "203 Mountain Way", "South Berlin", "MA", "01549"),
    STUDENT44("STDNT", "777770044", "student44", "grice@kuali.edu", "student44", "grice", true, "History", "Gregory", "H", "Rice", "Male", "Mr.", "", "107 Turnagain Bluff Way", "Hopedale", "MA", "01747"),
    STUDENT45("STDNT", "777770045", "student45", "jlawrence@kuali.edu", "student45", "jlawrence", true, "Linguistics", "Jeanette", "V", "Lawrence", "Female", "Ms.", "", "89 Moose Place", "Rockport", "MA", "01966"),
    STUDENT46("STDNT", "777770046", "student46", "dgomez@kuali.edu", "student46", "dgomez", true, "Mathematics", "Darlene", "O", "Gomez", "Female", "Ms.", "", "140 Ursa Minor Circle", "Worthington", "MA", "01098"),
    STUDENT47("STDNT", "777770047", "student47", "dburke@kuali.edu", "student47", "dburke", true, "Mechanical", "Dana", "S", "Burke", "Female", "Ms.", "", "175 Winter Ridge Court", "South Orleans", "MA", "02662"),
    STUDENT48("STDNT", "777770048", "student48", "dlewis@kuali.edu", "student48", "dlewis", true, "Music", "Dorothy", "D", "Lewis", "Female", "Ms.", "", "201 Alyeska Avenue West", "East Orleans", "MA", "02643"),
    STUDENT49("STDNT", "777770049", "student49", "sfitzgerald@kuali.edu", "student49", "sfitzgerald", true, "Philosophy", "Stephanie", "O", "Fitzgerald", "Female", "Ms.", "", "150 Salix Circle", "Worcester", "MA", "01610"),
    STUDENT50("STDNT", "777770050", "student50", "gperry@kuali.edu", "student50", "gperry", true, "Physics", "Glenn", "Q", "Perry", "Male", "Mr.", "", "103 Chuck Drive", "South Yarmouth", "MA", "02664"),
    STUDENT51("STDNT", "", "student51", "bdixon@kuali.edu", "student51", "bdixon", true, "PolySci", "Barry", "O", "Dixon", "Male", "Mr.", "", "149 Churchill Drive", "Norfolk", "MA", "02056"),
    STUDENT52("STDNT", "777770052", "student52", "lwilkerson@kuali.edu", "student52", "lwilkerson", true, "Psychology", "Larry", "N", "Wilkerson", "Male", "Mr.", "", "230 Hardwood Court", "Bedford", "MA", "01731"),
    STUDENT53("STDNT", "777770053", "student53", "cmorrison@kuali.edu", "student53", "cmorrison", true, "Sociology", "Calvin", "B", "Morrison", "Male", "Mr.", "", "255 Valarian Street", "Milton Village", "MA", "02187"),
    STUDENT54("STDNT", "777770054", "student54", "jgriffin@kuali.edu", "student54", "jgriffin", true, "AfricanAmericanStudies", "Jenny", "Q", "Griffin", "Female", "Ms.", "", "128 Dare Avenue", "Georgetown", "MA", "01833"),
    STUDENT55("STDNT", "777770055", "student55", "icampbell@kuali.edu", "student55", "icampbell", true, "AnimalAvianSciences", "Irene", "R", "Campbell", "Female", "Ms.", "", "99 East Eagle River Loop Road", "North Chelmsford", "MA", "01863"),
    STUDENT56("STDNT", "777770056", "student56", "erios@kuali.edu", "student56", "erios", true, "Athletics", "Elsa", "J", "Rios", "Female", "Ms.", "", "183 Happy Lane", "Babson Park", "MA", "02157"),
    STUDENT57("STDNT", "777770057", "student57", "aortiz@kuali.edu", "student57", "aortiz", true, "Biochemistry", "Alfred", "I", "Ortiz", "Male", "Mr.", "", "124 Tolsona Circle", "West Chatham", "MA", "02669"),
    STUDENT58("STDNT", "777770058", "student58", "cmontgomery@kuali.edu", "student58", "cmontgomery", true, "Biology", "Carla", "S", "Montgomery", "Female", "Ms.", "", "177 West Tudor Road", "Harwich Port", "MA", "02646"),
    STUDENT59("STDNT", "777770059", "student59", "ncummings@kuali.edu", "student59", "ncummings", true, "Botany", "Nathaniel", "O", "Cummings", "Male", "Mr.", "", "147 Monroe Avenue", "Dennis", "MA", "02638"),
    STUDENT60("STDNT", "777770060", "student60", "swright@kuali.edu", "student60", "swright", true, "CareerServices", "Shawn", "A", "Wright", "Male", "Mr.", "", "252 Bow Circle", "Worcester", "MA", "01546"),
    STUDENT61("STDNT", "777770061", "student61", "drobbins@kuali.edu", "student61", "drobbins", true, "Chemical", "Darren", "O", "Robbins", "Male", "Mr.", "", "238 Silver Spruce Drive", "Norwell", "MA", "02061"),
    STUDENT62("STDNT", "777770062", "student62", "tpowell@kuali.edu", "student62", "tpowell", true, "Chemistry", "Terry", "O", "Powell", "Male", "Mr.", "", "241 Voyles Boulevard", "Buzzards Bay", "MA", "02542"),
    STUDENT63("STDNT", "777770063", "student63", "pthompson@kuali.edu", "student63", "pthompson", true, "Civil", "Paul", "Q", "Thompson", "Male", "Mr.", "", "260 Elmendorf Drive", "Lawrence", "MA", "01842"),
    STUDENT64("STDNT", "777770064", "student64", "chobbs@kuali.edu", "student64", "chobbs", true, "CompSci", "Clyde", "V", "Hobbs", "Male", "Mr.", "", "107 Turnagain Parkway", "East Walpole", "MA", "02032"),
    STUDENT65("STDNT", "777770065", "student65", "cadams@kuali.edu", "student65", "cadams", true, "CrimCrimJus", "Christine", "R", "Adams", "Female", "Ms.", "", "286 Lennie Circle", "Middlesex Essex Gmf", "MA", "01807"),
    STUDENT66("STDNT", "777770066", "student66", "jday@kuali.edu", "student66", "jday", true, "Economics", "Judy", "I", "Day", "Female", "Ms.", "", "9 Falling Water Circle", "West Somerville", "MA", "02144"),
    STUDENT67("STDNT", "777770067", "student67", "scole@kuali.edu", "student67", "scole", true, "English", "Sam", "S", "Cole", "Male", "Mr.", "", "42 Oney Circle", "Prides Crossing", "MA", "01965"),
    STUDENT68("STDNT", "777770068", "student68", "cmeyers@kuali.edu", "student68", "cmeyers", true, "FineArts", "Cristina", "O", "Meyers", "Female", "Ms.", "", "292 Anne Circle", "Williamstown", "MA", "01267"),
    STUDENT69("STDNT", "777770069", "student69", "creed@kuali.edu", "student69", "creed", true, "French", "Carlos", "F", "Reed", "Male", "Mr.", "", "268 Wakefield Circle", "Rehoboth", "MA", "02769"),
    STUDENT70("STDNT", "777770070", "student70", "cperkins@kuali.edu", "student70", "cperkins", true, "Geography", "Cathy", "K", "Perkins", "Female", "Ms.", "", "296 East 86th Avenue", "Glendale", "MA", "01229"),
    STUDENT71("STDNT", "777770071", "student71", "mlittle@kuali.edu", "student71", "mlittle", true, "Geology", "Melanie", "Q", "Little", "Female", "Ms.", "", "158 Teri Drive", "Woods Hole", "MA", "02543"),
    STUDENT72("STDNT", "777770072", "student72", "aphelps@kuali.edu", "student72", "aphelps", true, "History", "Alberta", "H", "Phelps", "Female", "Ms.", "", "204 Ephreta Court", "North Attleboro", "MA", "02761"),
    STUDENT73("STDNT", "777770073", "student73", "abartlett@kuali.edu", "student73", "abartlett", true, "Linguistics", "Anthony", "I", "Bartlett", "Male", "Mr.", "", "185 Saint Elias Drive", "Chesterfield", "MA", "01012"),
    STUDENT74("STDNT", "777770074", "student74", "kcastillo@kuali.edu", "student74", "kcastillo", true, "Mathematics", "Kevin", "C", "Castillo", "Male", "Mr.", "", "37 Heatherbrook Circle", "Boston", "MA", "02241"),
    STUDENT75("STDNT", "777770075", "student75", "cbrown@kuali.edu", "student75", "cbrown", true, "Mechanical", "Chris", "P", "Brown", "Male", "Mr.", "", "22 Asterion Circle", "Newtonville", "MA", "02160"),
    STUDENT76("STDNT", "777770076", "student76", "tharrison@kuali.edu", "student76", "tharrison", true, "Music", "Thelma", "M", "Harrison", "Female", "Ms.", "", "290 Castle Court", "Charlton Depot", "MA", "01509"),
    STUDENT77("STDNT", "777770077", "student77", "tharmon@kuali.edu", "student77", "tharmon", true, "Philosophy", "Tom", "H", "Harmon", "Male", "Mr.", "", "243 Harrier Circle", "Chatham", "MA", "02633"),
    STUDENT78("STDNT", "777770078", "student78", "llarson@kuali.edu", "student78", "llarson", true, "Physics", "Laurie", "I", "Larson", "Female", "Ms.", "", "83 Boeing Lane", "North Grafton", "MA", "01536"),
    STUDENT79("STDNT", "777770079", "student79", "jhawkins@kuali.edu", "student79", "jhawkins", true, "PolySci", "Jody", "N", "Hawkins", "Female", "Ms.", "", "1 Idaho Street", "Yarmouth Port", "MA", "02675"),
    STUDENT80("STDNT", "777770080", "student80", "mhardy@kuali.edu", "student80", "mhardy", true, "Psychology", "Marsha", "N", "Hardy", "Female", "Ms.", "", "130 Fountain Drive", "Springfield", "MA", "01152"),
    AFFILIATE1("AFLT", "666660001", "affiliate1", "kingram@kuali.edu", "affiliate1", "kingram", true, "Sociology", "Kayla", "J", "Ingram", "Female", "Ms.", "", "283 Boysen Berry Place", "Northampton", "MA", "01063"),
    AFFILIATE2("AFLT", "666660002", "affiliate2", "pchristensen@kuali.edu", "affiliate2", "pchristensen", true, "AfricanAmericanStudies", "Paul", "Q", "Christensen", "Male", "Mr.", "", "253 Echo Street", "Sheffield", "MA", "01257"),
    AFFILIATE3("AFLT", "", "affiliate3", "jboyd@kuali.edu", "affiliate3", "jboyd", true, "AnimalAvianSciences", "Jean", "A", "Boyd", "Female", "Ms.", "", "96 Hunter Drive", "Thorndike", "MA", "01079"),
    AFFILIATE4("AFLT", "", "affiliate4", "cclark@kuali.edu", "affiliate4", "cclark", true, "Athletics", "Carlos", "U", "Clark", "Male", "Mr.", "", "127 East 142nd Avenue", "Waverley", "MA", "02179"),
    AFFILIATE5("AFLT", "666660005", "affiliate5", "mbyrd@kuali.edu", "affiliate5", "mbyrd", true, "Biochemistry", "Mario", "H", "Byrd", "Male", "Mr.", "", "143 Neptune Court", "Hanson", "MA", "02341"),
    AFFILIATE6("AFLT", "", "affiliate6", "dreynolds@kuali.edu", "affiliate6", "dreynolds", true, "Biology", "Duane", "O", "Reynolds", "Male", "Mr.", "", "201 East Stonehill Drive", "Newton Highlands", "MA", "02161"),
    AFFILIATE7("AFLT", "", "affiliate7", "fhood@kuali.edu", "affiliate7", "fhood", true, "Botany", "Francis", "D", "Hood", "Female", "Ms.", "", "10 Rocky Cove Drive", "Colrain", "MA", "01340"),
    AFFILIATE8("AFLT", "666660008", "affiliate8", "cwatson@kuali.edu", "affiliate8", "cwatson", true, "CareerServices", "Curtis", "R", "Watson", "Male", "Mr.", "", "229 Fenn Street", "Boston", "MA", "02123"),
    AFFILIATE9("AFLT", "666660009", "affiliate9", "vruiz@kuali.edu", "affiliate9", "vruiz", true, "Chemical", "Vincent", "R", "Ruiz", "Male", "Mr.", "", "179 Glacier Road", "Buckland", "MA", "01338"),
    AFFILIATE10("AFLT", "666660010", "affiliate10", "dprice@kuali.edu", "affiliate10", "dprice", true, "Chemistry", "Derek", "I", "Price", "Male", "Mr.", "", "156 Groh Street", "Lowell", "MA", "01851");
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



import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * @author Lance Baker c3128034
 */
public class Theatre extends HttpServlet {

    private static final String DATE_TIME_FORMAT = "dd-MM-yy ss:mm:hh";
    private static HashMap<String, Seat> seats = new HashMap<String, Seat>();
    private String securityCode; // An instance String field to store the generated unique security code.

    /**
     * The pageHeader() static method is used to create the HTML header that is used across the system.
     * It is called processRequest method, and remains public incase it will be used via another class.
     * @return String - The generated page HTML header.
     */
    public static String pageHeader() {
        String docType =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n";
        return (docType
                + "	<head>\n"
                + "       <title>Online Seat Booking System</title>\n"
                + "       <meta name=\"description\" content=\"Seat Booking System\" />\n"
                + "       <link rel=\"stylesheet\" type=\"text/css\" href=\"/styles.css\" media=\"screen\" />\n"
                + "       <script type=\"text/javascript\" src=\"/theatre.js\"></script>\n"
                + "	</head>\n"
                + "       <body>\n"
                + "       <div id=\"main_container\">\n"
                + "           <div id=\"head_container\">\n"
                + "		<div id=\"banner\"></div>\n"
                + "		<div id=\"banner_shadow\"></div>\n"
                + "           </div>\n"
                + "           <div id=\"content_container\">\n"
                + "		<div id=\"menu_container\">\n"
                + "			<div id=\"menu_top\"></div>\n"
                + "			<div id=\"menu_repeat\">\n"
                + "                            <a href=\"Theatre\" class=\"menu_main\"></a>\n"
                + "			</div>\n"
                + "			<div id=\"menu_footer\"></div>\n"
                + "		</div>\n"
                + "		<div id=\"body_container\">\n"
                + "			<div id=\"body_top\">"
                + "                         <div id=\"date_time\">" + getCurrentTime() + "</div>"
                + "                     </div>\n"
                + "			<div id=\"body_repeat\">\n");
    }

    /**
     * The pageFooter static method is used to create the page footer. It is called via the processRequest method.
     * @return String - The generated page HTML footer.
     */
    public static String pageFooter() {
        return ("					</div>\n"
                + "					<div id=\"body_footer\"></div>\n"
                + "					<p>"
                + "					    <a href=\"http://jigsaw.w3.org/css-validator/check/referer\">"
                + "					    <img style=\"border:0;width:88px;height:31px\""
                + "					             src=\"http://jigsaw.w3.org/css-validator/images/vcss-blue\""
                + "					             alt=\"Valid CSS!\" />"
                + "					    </a>"
                + "					    <a href=\"http://validator.w3.org/check?uri=referer\">"
                + "					     <img src=\"http://www.w3.org/Icons/valid-xhtml10\" alt=\"Valid XHTML 1.0 Strict\" height=\"31\" width=\"88\" /></a>"
                + "					</p>"
                + "				</div>\n"
                + "			</div>\n"
                + "		</div>\n"
                + "	</body>\n"
                + "</html>\n");
    }

    /**
     * The getCurrentTime method is used to return a datetime String of the current time
     * in the following format "DD-MM-YY SS:MM:HH".
     * @return String - The current datetime.
     */
    private static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(cal.getTime());
    }

    /**
     * The generateSecurityCode method is used to create a unique security code which is used to 
     * validate human authenticity when creating a booking. It uses the substring to fetch a unique set
     * of 6 characters, and sets the String to the instance securityCode variable. It gets invoked by
     * the bookingForm method.
     */
    private void generateSecurityCode() {
        this.securityCode = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(3, 9).toUpperCase();
    }

    /**
     * The bookingForm method receives a String id of the seat that the user has selected. It then generates
     * the unique security code for the form human validation, and then structures the HTML output using a 
     * StringBuilder. The received id is then placed in a hidden field that is then passed back to the server when
     * the form is submitted to verify the seat submission. It gets invoked by the processRequest method.
     * @param id String - The received String id of the desired seat position.
     * @return String - The HTML form.
     */
    private String bookingForm(String id) {
        this.generateSecurityCode();

        StringBuilder builder = new StringBuilder();
        builder.append("<div id=\"errors\" class=\"error_message\" style=\"display: none\"></div>");
        builder.append("<div id=\"box_container\">\n");
        builder.append("	<div id=\"box_header\">\n");
        builder.append("	<div id=\"box_title\">Seat ").append(id).append(" Booking Request</div>\n");
        builder.append("	</div>\n");
        builder.append("	<div id=\"box_repeat\">\n");

        builder.append("<form id=\"booking\" action=\"Theatre\" onsubmit=\"return validateForm(this);\" method=\"POST\" class=\"booking_form\">\n");
        builder.append("<p>\n<label for=\"username\">User ID *:</label>\n<input type=\"text\" id=\"username\" name=\"username\" size=\"20\" />\n</p>\n");
        builder.append("<p>\n<label for=\"name\">Name *:</label>\n<input type=\"text\" id=\"name\" name=\"name\" size=\"20\" />\n</p>\n");
        builder.append("<p>\n<label for=\"phone\">Phone:</label>\n<input type=\"text\" id=\"phone\" name=\"phone\" size=\"20\" />\n</p>\n");
        builder.append("<p>\n<label for=\"address\">Address:</label>\n<input type=\"text\" id=\"address\" name=\"address\" size=\"20\" />\n</p>\n");
        builder.append("<p>\n<label for=\"email\">Email *:</label>\n<input type=\"text\" id=\"email\" name=\"email\" size=\"20\" />\n</p>\n");
        builder.append("<p>\n<label for=\"email\">Security Code *:</label>\n<input type=\"text\" id=\"security_code\" name=\"security_code\" size=\"20\" /> ").append(this.securityCode).append("\n</p>\n");
        builder.append("<div style=\"margin-left: 150px;\">\n");
        builder.append("<input type=\"submit\" value=\"Submit\" /> <input type=\"reset\" value=\"Clear\" />\n</div>\n");
        builder.append("<input type=\"hidden\" name=\"id\" value=\"").append(id).append("\" />");
        builder.append("<input type=\"hidden\" id=\"code\" name=\"code\" value=\"").append(this.securityCode).append("\" /> ");
        builder.append("</form>\n");

        builder.append("	</div>\n");
        builder.append("	<div id=\"box_footer\"></div>\n");
        builder.append("</div>\n");
        return builder.toString();
    }

    /**
     * The viewSeat method is used to output the contents of the received Seat object customer data.
     * @param seat Seat- The customer data of the Seat.
     * @return String - The received Seat object details outputted in HTML.
     */
    private String viewSeat(Seat seat) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div id=\"seat_info_bg\">");
        builder.append("<div class=\"field_label\">Name:</div><div class=\"field_value\">&nbsp;").append(seat.getName()).append("</div>");
        if (!seat.getPhone().equals("")) {
            builder.append("<div class=\"field_label\">Phone:</div><div class=\"field_value\">&nbsp;").append(seat.getPhone()).append("</div>");
        }
        if (!seat.getAddress().equals("")) {
            builder.append("<div class=\"field_label\">Address:</div><div class=\"field_value\">&nbsp;").append(seat.getAddress()).append("</div>");
        }
        builder.append("<div class=\"field_label\">Email:</div><div class=\"field_value\">&nbsp;").append(seat.getEmail()).append("</div>");
        builder.append("<div class=\"field_label\">Booking Time:</div><div class=\"field_value\">&nbsp;").append(seat.getBookingTime()).append("</div>");
        builder.append("</div>");
        return builder.toString();
    }

    /**
     * The getSeats() method is used to create the Theatre seating area. It uses two for loops
     * for iterating throughout the rows. Each seat is a hyper-link to load the booking page or
     * if a person is on the seat; then seat viewing mode will be enabled so the customer details will be shown.
     * @return String - The generated HTML code of the Theatre seating area.
     */
    private String getSeats() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div id=\"column_labels\"></div>\n");
        builder.append("<div id=\"row_labels\"></div>\n");
        builder.append("<div id=\"floor_container\">\n");
        builder.append("    <div id=\"floor_top\"></div>\n");
        builder.append("    <div id=\"floor_repeat\">\n");

        for (char l = 'A'; l <= 'H'; l++) { // Iterates through each row letter.
            builder.append("\n<div class=\"seat_row\">\n"); // Creates a div container for the row.
            for (int i = 1; i <= 8; i++) { // Iterates through the column number.
                String id = l + "" + i; // Creates the seat ID.
                // Creates the link around the ID & person.
                builder.append("<a href=\"Theatre?seat=").append(id).append("\"><div class=\"seat\">");
                if (seats.containsKey(id)) {
                    builder.append("<div class=\"person\">").append(id).append("</div>");
                } else {
                    builder.append(id);
                }
                builder.append("</div></a>\n");
            }
            builder.append("</div>\n");
        }
        builder.append("    </div>\n");
        builder.append("    <div id=\"floor_bottom\"></div>\n");
        builder.append("</div>\n");

        return builder.toString(); // Returns as a String.
    }

    /**
     * The canBookMore method receives the username that the user uses when booking a seat.
     * It counts how many times the username has been used on the other seats, and if it has been
     * used 3 times previously then it will return a boolean false - otherwise it will return true.
     * @param username
     * @return boolean - indicating whether they can book again.
     */
    private boolean canBookMore(String username) {
        int count = 0;
        for (Seat seat : seats.values()) {
            if (username.equalsIgnoreCase(seat.getUsername())) {
                count++;
            }
        }
        return (count < 3);
    }

    /**
     * The addBooking method is used to receive the booking submission, processes it, and
     * stores the newly instantiated object into the HashMap based on the seat position id.
     * It first checks to ensure that the record was submitted by ensuring that it was submitted
     * with an id. It then checks whether the received username can book anymore seats, and if they
     * can't then it will display an appropriate error message stating otherwise.
     * @param request - The values posted via the form are stored in this object.
     * @param response - To enable for displaying output back.
     * @throws IOException 
     */
    private void addBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (request.getParameter("id") != null) { // Checks to ensure a record was submitted.
            // Checks to ensure the user can book again.
            if (this.canBookMore(request.getParameter("username"))) {
                Seat seat = new Seat();
                seat.setUsername(request.getParameter("username"));
                seat.setName(request.getParameter("name"));
                seat.setPhone(request.getParameter("phone"));
                seat.setAddress(request.getParameter("address"));
                seat.setEmail(request.getParameter("email"));
                seat.setBookingTime(getCurrentTime());
                // Places the booked seat into the HashMap.
                seats.put(request.getParameter("id"), seat);
            } else {
                out.println("<div class=\"error_message\">Sorry. You cannot book anymore.</div><br />");
            }
        }
    }

    /**
     * The processRequest method is used by the doGet and doPost. It is pretty much the most detrimental
     * method in the entire system, and is solely responsible for handling all the requests made to and 
     * from the Servlet. It first displays the pageHeader(), and then determines whether the user wants
     * to view a seat (and if the seat hasn't already been taken) it enables them to place a booking by 
     * presenting them with the bookingForm(). However, if a seat was selected and it exists in the HashMap
     * then the user wishes to view a seat so it shows them it via the viewSeat method. Otherwise, it will attempt
     * to add a new booking via addBooking().
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(pageHeader());
        String seat = request.getParameter("seat");
        if (seat != null && (!seats.containsKey(seat))) {
            out.println(this.bookingForm(seat));
        } else {
            if (seat != null && seats.containsKey(seat)) {
                out.println(this.viewSeat(seats.get(seat)));
            } else {
                addBooking(request, response);
            }
            out.println(this.getSeats());
        }
        out.println(pageFooter());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.registration.client.service.dto.AddCourseToCartRequestInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Services for Registration Cart Operations
 */
@Path("/")
public interface CourseRegistrationCartClientService {

    /**
     * Looks up cart information for the given user and term. if no cart exists, one is created
     *
     * @param termId term for the cart
     * @return a Cart Result that contains the courses a student is intending to enroll in for the given term.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/cart")
    public Response searchForCartRS(@QueryParam("termId") String termId);

    /**
     * Find cart if it exists and delete all items from cart.
     *
     * @param termId   - optional, but more efficient to use
     * @param termCode  - optional, but human readable. 201208
     * @return
     */
    @DELETE
    @Path("/cart")
    public Response clearCartRS(@QueryParam("termId") String termId,
                                @QueryParam("termCode") String termCode);

    /**
     * The REST version of submitCart
     *
     * @param termId          ID of the term for the cart
     * @return A Response containing the Boolean value of TRUE or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/cart/submit")
    public Response submitCartRS(@QueryParam("termId") String termId);


    /**
     * The REST version of addCourseToCart
     *
     * @param termId          ID of the term for the cart
     * @param courseCode      course offering to add to the cart
     * @param regGroupId      Optional. Will speed things up if passed instead of (courseCode, regGroupCode)
     * @param regGroupCode    reg group we want to add
     * @param gradingOptionId the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @param credits         The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @return Response containing the cart item that was updated or a server error response.
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/cart/items")
    public Response addCourseToCartRS(@FormParam("termId") String termId,
                                      @FormParam("regGroupId") String regGroupId,
                                      @FormParam("courseCode") String courseCode,
                                      @FormParam("regGroupCode") String regGroupCode,
                                      @FormParam("gradingOptionId") String gradingOptionId,
                                      @FormParam("credits") String credits);

    /**
     * The REST version of removeItemFromCart
     *
     * @param termId     ID of the term for the cart
     * @param cartItemId id of the item to delete from the cart.
     * @return Response(with add action link) containing the cart item that was updated or a server error response. As Well as action links to undo the removal.
     */
    @DELETE
    @Path("/cart/items")
    public Response removeItemFromCartRS(@QueryParam("termId") String termId,
                                         @QueryParam("cartItemId") String cartItemId);

    /**
     * The REST version of updateCartItem
     *
     * @param termId     ID of the term for the cart
     * @param cartItemId ID of the specific item being updated
     * @param credits    The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @param gradingOptionId    the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @return Response containing the cart item that was updated or a server error response.
     */
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/cart/items")
    public Response updateCartItemRS(@FormParam("termId") String termId,
                                     @FormParam("cartItemId") String cartItemId,
                                     @FormParam("credits") String credits,
                                     @FormParam("gradingOptionId") String gradingOptionId);

    /**
     * Adds a list of courses to the cart for the given user and term.
     *
     * @param termId term for the cart
     * @param cartItems List of AddCourseToCartRequestInfo to be added
     * @return Response containing a list of the cart items that were updated or a server error response.
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/cart/items/list")
    public Response addCoursesToCartRS(@QueryParam("termId") String termId,
                                       List<AddCourseToCartRequestInfo> cartItems);

    /**
     * The REST version of undoDeleteCourse
     * Calls addCourseToCart logic via a GET Query
     * This is necessary because Undo is a link which does not allow POST operations or have Form Parameters
     *
     * @param termId          termId for reg group being added to cart
     * @param courseCode      course offering to add to the cart
     * @param regGroupId      Optional. Will speed things up if passed instead of (courseCode, regGroupCode)
     * @param regGroupCode    reg group we want to add
     * @param gradingOptionId the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @param credits         The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @return Response containing the cart item that was updated or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/undoDeleteCourse")
    public Response undoDeleteCourseRS(@QueryParam("termId") String termId,
                                       @QueryParam("courseCode") String courseCode,
                                       @QueryParam("regGroupId") String regGroupId,
                                       @QueryParam("regGroupCode") String regGroupCode,
                                       @QueryParam("gradingOptionId") String gradingOptionId,
                                       @QueryParam("credits") String credits);




    /** ------------------ ALIAS METHODS --------------------- */

    /**
     * Find cart if it exists and delete all items from cart.
     *
     * @deprecated
     * @param termId   - optional, but more efficient to use
     * @param termCode  - optional, but human readable. 201208
     * @return
     */
    @GET
    @Path("/clearCart")
    public Response clearCartAliasRS(@QueryParam("termId") String termId,
                                     @QueryParam("termCode") String termCode);

    /**
     * Looks up cart information for the given user and term. if no cart exists, one is created
     *
     * @deprecated
     * @param termId term for the cart
     * @return a Cart Result that contains the courses a student is intending to enroll in for the given term.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/searchForCart")
    public Response searchForCartAliasRS(@QueryParam("termId") String termId);

    /**
     * The REST version of submitCart
     *
     * @deprecated
     * @param cartId ID of the registrationRequest representing the cart
     * @return A Response containing the Boolean value of TRUE or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/submitCart")
    public Response submitCartAliasRS(@QueryParam("cartId") String cartId);

    /**
     * The REST version of addCourseToCart
     *
     * @deprecated
     * @param courseCode      course offering to add to the cart
     * @param regGroupId      Optional. Will speed things up if passed instead of (courseCode, regGroupCode)
     * @param regGroupCode    reg group we want to add
     * @param gradingOptionId the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @param credits         The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @return Response containing the cart item that was updated or a server error response.
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/addCourseToCart")
    public Response addCourseToCartAliasRS(@FormParam("termId") String termId,
                                           @FormParam("regGroupId") String regGroupId,
                                           @FormParam("courseCode") String courseCode,
                                           @FormParam("regGroupCode") String regGroupCode,
                                           @FormParam("gradingOptionId") String gradingOptionId,
                                           @FormParam("credits") String credits);

}



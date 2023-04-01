package id.agit.book;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstant {

    public static final String API_V1 = "/api/v1";
    public static final String GET = "/get";
    public static final String GET_BY_STATUS = "/get-by-status/{status}";
    public static final String CREATE = "/create";
    public static final String UPDATE_BOOK = "/update-book";
    public static final String UPDATE_STATUS = "/update-status";
    public static final String BORROW = "/borrow";
    public static final String DELETE = "/delete";

}
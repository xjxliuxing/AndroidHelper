package com.xjx.helper.http.retrofit.myRetrofit;

import retrofit2.Response;

/** @deprecated Use {@link retrofit2.HttpException}. */
@Deprecated
public final class HttpException extends retrofit2.HttpException {
  private String errorMessage;
  public HttpException(Response<?> response,String errorMessage) {
    super(response);
    this.errorMessage=errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}

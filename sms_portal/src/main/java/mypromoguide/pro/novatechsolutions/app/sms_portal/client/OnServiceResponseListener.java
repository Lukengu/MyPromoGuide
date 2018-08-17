package mypromoguide.pro.novatechsolutions.app.sms_portal.client;

public interface OnServiceResponseListener<T>  {

    void onSuccess(T object);
    void onFailure(ClientException e);
}

package github.otowave.api.controlers;

public interface Customizable {
    void upload();
    void delete();
    void changeImage(int sourceID, int prevImageID);
    void changeName();
    void changeTale();
}
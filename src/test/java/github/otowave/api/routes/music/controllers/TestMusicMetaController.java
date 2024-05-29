package github.otowave.api.routes.music.controllers;

import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import github.otowave.api.routes.music.contollers.MusicCustomizeController;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = MusicCustomizeController.class)
public class TestMusicMetaController {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ItemFactoryImp itemFactoryImp;

    @MockBean
    private MusicMetaRepo yourItemRepository;

    @Test
    public void testChangeName() {
        int itemID = 8;
        String newName = "New Item Name";
        String authToken = "1";

/*        Mono<Item> mockItem = itemFactoryImp.makeCloseItem(MUSIC, 8, "1");  // Replace with actual item initialization
        when(itemFactoryImp.makeCloseItem(MUSIC, anyInt(), anyString()))
                .thenReturn(mockItem);

        when(mockItem.changeName(anyString())).thenReturn(Mono.empty());*/

        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/music/"+itemID+"/change-name")
                        .queryParam("newName", newName)
                        .build())
                .cookie("authToken", authToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }
}

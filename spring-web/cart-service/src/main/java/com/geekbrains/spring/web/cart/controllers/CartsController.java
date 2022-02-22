package com.geekbrains.spring.web.cart.controllers;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.dto.StringResponse;
import com.geekbrains.spring.web.cart.converters.CartConverter;
import com.geekbrains.spring.web.cart.exceptions.CartIsBrokenException;
import com.geekbrains.spring.web.cart.models.Cart;
import com.geekbrains.spring.web.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина", description = "Методы работы с корзиной")
public class CartsController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @Operation(
            summary = "Запрос на получение корзины по имени пользователя или UUID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            }
    )
    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) @Parameter(description = "Имя клиента") String username, @PathVariable @Parameter(description = "Идентификатор корзины UUID") String uuid) {
        return cartConverter.modelToDto(cartService.getCurrentCart(getCurrentCartUuid(username, uuid)));
    }

    @Operation(
            summary = "Генерация корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @Operation(
            summary = "Добавить продукт в корзину по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = void.class))
                    ) ,
                    @ApiResponse(
                            description = "Нет ответа МС Продукты", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = void.class))
                    ) ,
                    @ApiResponse(
                            description = "евозможно добавить продукт в корзину. Продукт не найдет, id ...", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = void.class))
                    )
            }
    )
    @GetMapping("/{uuid}/add/{productId}")
    public void add(@RequestHeader(required = false) @Parameter(description = "Имя клиента") String username, @PathVariable @Parameter(description = "UUID корзины") String uuid, @PathVariable @Parameter(description = "ID продукта") Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }

    @Operation(
            summary = "Удалить 1 позицию продукта из корзины по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = void.class))
                    )
            }
    )
    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @Operation(
            summary = "Удалить продукт из корзины по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = void.class))
                    )
            }
    )
    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @Operation(
            summary = "Очистить корзину",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = void.class))
                    )
            }
    )
    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @Operation(
            summary = "Слияние корзины UUID с корзиной клиента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = void.class))
                    )
            }
    )
    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}

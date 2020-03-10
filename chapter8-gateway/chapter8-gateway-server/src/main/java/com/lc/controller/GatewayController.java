package com.lc.controller;

import com.lc.entity.Foo;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Gateway controller.
 *
 * @description:
 * @author zhuyangze
 * @date 2019/12/1
 */
@RestController
public class GatewayController {

    /**
     * Foo list.
     *
     * @return
     */
    @GetMapping("/test/123")
    public List<Foo> foos() {
       return Arrays.asList(new Foo("hello"));
    }

    /**
     * Foo entity.
     *
     * @param id
     * @param headers
     * @return
     */
    @GetMapping("/foos/{id}")
    public Foo foo(@PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        String custom = headers.getFirst("X-Custom");
        return new Foo(id == 1 ? "foo" : custom != null ? custom : "bye");
    }
}

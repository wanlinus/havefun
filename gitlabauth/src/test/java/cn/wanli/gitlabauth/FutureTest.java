package cn.wanli.gitlabauth;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author wanli
 * @date 2019-07-26 11:40
 */
public class FutureTest {
    @Test
    public void aa() {
        getPriceAsync("sad");
    }

    @Test
    public void vvv() {
        Long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        List<String> collect = list.stream().map(this::getPrAsync)
                .map(this::add)
                .map(this::getPrAsync)
                .collect(Collectors.toList());
        System.out.println(collect);
        Long end = System.currentTimeMillis();
        System.out.println(String.format("cast [%s]", (end - start) / 1000.0));
    }

    @Test
    public void vvv2() {
        Long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        List<CompletableFuture<String>> collect = list.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> getPrAsync(s)))
                .map(future -> future.thenApply(this::add))
                .map(future -> future.thenCompose(s -> CompletableFuture.supplyAsync(() -> getPrAsync(s))))
                .collect(Collectors.toList());

        List<String> collect1 = collect.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println(collect1);
        Long end = System.currentTimeMillis();
        System.out.println(String.format("cast [%s]", (end - start) / 1000.0));
    }

    @Test
    public void vvv3() {
        Long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        list.add("aaa");
        list.add("bbb");
        list.add("vvv");
        List<String> collect = list.stream().parallel()
                .map(this::getPrAsync)
                .map(this::add)
                .map(this::getPrAsync)
                .collect(Collectors.toList());
        System.out.println(collect);
        Long end = System.currentTimeMillis();
        System.out.println(String.format("cast [%s]", (end - start) / 1000.0));
    }


    public Future<String> getPriceAsync(String str) {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            delay();
            future.complete(str + "sss");
        }).start();
        return future;
    }

    String getPrAsync(String str) {
        delay();
        return str + "async";
    }

    public String add(String str) {
        return str + "add";
    }

    public void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

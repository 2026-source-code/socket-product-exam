package server;

import com.google.gson.Gson;
import dto.RequestDTO;
import dto.ResponseDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class MyServer {
    public static void main(String[] args) {
        try {
            // 1. 20000번 포트로 대기중...
            ServerSocket ss = new ServerSocket(20000);
            Socket socket = ss.accept();

            // 2. 새로운 소켓에 버퍼달기 (BR, BW)
            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            while(true){
                // 1. 클라이언트로부터 받은 메시지
                // 1. 상세보기 -> {"method":"get","querystring":{"id":1}}
                // 2. 목록보기 -> {"method":"get"}
                // 3. 삭제하기 -> {"method":"delete","querystring":{"id":1}}
                // 4. 등록하기 -> {"method":"post","body":{"name":"참외","price":1000,"qty":10}}
                String requestBody = br.readLine(); // 엔터키까지 읽기

                // 2. 파싱 (json string -> object)
                Gson gson = new Gson();
                RequestDTO request = gson.fromJson(requestBody, RequestDTO.class);
                System.out.println(request);

                // 3. 서비스호출 (상품상세)
                String responseBody = "";
                ProductService service = new ProductService();
                if(request.getMethod().equals("post")){ // 상품등록
                    String name = request.getBody().getName();
                    int price = request.getBody().getPrice();
                    int qty = request.getBody().getQty();
                    try {
                        service.상품등록(name, price, qty);
                        ResponseDTO response = new ResponseDTO();
                        response.setMsg("ok");
                        response.setBody(null);

                        responseBody = gson.toJson(response);
                    } catch (RuntimeException e) {
                        ResponseDTO response = new ResponseDTO();
                        response.setMsg(e.getMessage());
                        response.setBody(null);

                        responseBody = gson.toJson(response);
                    }

                }else if(request.getMethod().equals("delete")){ // 상품삭제
                    int id = request.getQuerystring().get("id");
                    try {
                        service.상품삭제(id);

                        ResponseDTO response = new ResponseDTO();
                        response.setMsg("ok");
                        response.setBody(null);

                        responseBody = gson.toJson(response);
                    } catch (RuntimeException e) {
                        ResponseDTO response = new ResponseDTO();
                        response.setMsg(e.getMessage());
                        response.setBody(null);

                        responseBody = gson.toJson(response);
                    }
                }else{
                    if(request.getQuerystring() != null){ // 상세보기
                        int id = request.getQuerystring().get("id");
                        try{
                            Product product = service.상품상세(id);

                            ResponseDTO<Product> response = new ResponseDTO();
                            response.setMsg("ok");
                            response.setBody(product);

                            responseBody = gson.toJson(response);
                        } catch (RuntimeException e) {
                            ResponseDTO<Product> response = new ResponseDTO();
                            response.setMsg(e.getMessage());
                            response.setBody(null);

                            responseBody = gson.toJson(response);
                        }

                    }else{ // 전체보기
                        List<Product> list = service.상품목록();

                        ResponseDTO<List<Product>> response = new ResponseDTO();
                        response.setMsg("ok");
                        response.setBody(list);

                        responseBody = gson.toJson(response);
                    }
                }

                // 4. 응답
                bw.write(responseBody);
                bw.write("\n");
                bw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
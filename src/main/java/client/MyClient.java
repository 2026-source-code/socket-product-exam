package client;

import com.google.gson.Gson;
import dto.RequestDTO;
import server.Product;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyClient {
    public static void main(String[] args) {
        try {
            // 1. Socket 연결 완료
            Socket socket = new Socket("localhost", 20000);

            // 2. 키보드 입력 버퍼
            InputStream keyStream = System.in;
            InputStreamReader keyReader = new InputStreamReader(keyStream);
            BufferedReader keyBuf = new BufferedReader(keyReader);

            // 3. BW 버퍼
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            // 4. BR 버퍼
            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);

            // 1. 상세보기 -> get 1
            // 2. 목록보기 -> get
            // 3. 삭제하기 -> delete 1
            // 4. 등록하기 -> post 바나나 5000 20
            while(true){
                // 요청
                String keyboardData = keyBuf.readLine();

                // 파싱!!
                String[] token = keyboardData.split(" ");
                int size = token.length;
                String method = token[0];

                Gson gson = new Gson();
                RequestDTO request = new RequestDTO();
                String requestBody = "";
                if(method.equals("post")){
                    // 1. 사용자의 데이터를 파싱해서 받기
                    String name = token[1];
                    int price = Integer.parseInt(token[2]);
                    int qty = Integer.parseInt(token[3]);

                    // 2. 서버로 전송하기 전에 클래스로 만들기
                    request.setMethod(method);
                    request.setQuerystring(null);
                    request.setBody(new Product(null, name, price, qty));

                    // 3. 문자열로 변경하기 (json이 프로토콜이니까)
                    requestBody = gson.toJson(request);

                }else if(method.equals("delete")){
                    int id = Integer.parseInt(token[1]);

                    // 2. 서버로 전송하기 전에 클래스로 만들기
                    Map<String, Integer> map = new HashMap<>();
                    map.put("id", id);

                    request.setMethod(method);
                    request.setQuerystring(map);
                    request.setBody(null);

                    // 3. 문자열로 변경하기 (json이 프로토콜이니까)
                    requestBody = gson.toJson(request);
                }else{
                    Integer id = null;
                    if(size > 1){
                        id = Integer.parseInt(token[1]);
                    }

                    if(id == null){ // get
                        // 2. 서버로 전송하기 전에 클래스로 만들기
                        request.setMethod(method);
                        request.setQuerystring(null);
                        request.setBody(null);

                        // 3. 문자열로 변경하기 (json이 프로토콜이니까)
                        requestBody = gson.toJson(request);
                    }else{ // get 1
                        // 2. 서버로 전송하기 전에 클래스로 만들기
                        Map<String, Integer> map = new HashMap<>();
                        map.put("id", id);

                        request.setMethod(method);
                        request.setQuerystring(map);
                        request.setBody(null);

                        // 3. 문자열로 변경하기 (json이 프로토콜이니까)
                        requestBody = gson.toJson(request);
                    }
                }
                //System.out.println("클라이언트측에서 최종적으로 보내는 메시지");
                //System.out.println(requestBody);
                bw.write(requestBody);
                bw.write("\n");
                bw.flush();

                // 응답
                String line = br.readLine();
                System.out.println(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

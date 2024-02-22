package org.edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpCalculatorServer {

    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }

        Boolean running = true;
        while(running){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            Boolean firstLine = true;
            String uriString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (firstLine){
                    uriString = inputLine.split(" ")[1];
                    firstLine = false;
                }
                if (!in.ready()) {break; }
            }
            outputLine  = "";
            URI requestURI = new URI(uriString);
            String path = requestURI.getPath();

            if(path.equals("/compreflex")){
                if(!requestURI.getQuery().equals(null)) {
                    System.out.println(requestURI.getQuery());
                    String command = requestURI.getQuery().split("=")[1];
                    if (command.startsWith("Class")) {
                        String className = command.split("Class")[1];
                        className = className.substring(1, className.length() - 1);
                        Class<?> classImp = Class.forName(className);
                        Method[] declaredMethods = classImp.getDeclaredMethods();
                        Field[] declaredFields = classImp.getDeclaredFields();
                        String outputMethods = "{\"DeclaredMethods\": [";
                        for (Method method : declaredMethods) {
                            outputMethods += method.getName() + ",";
                        }
                        String outputFields = "{\"DeclaredFields\": [";
                        for (Field field : declaredFields) {
                            outputFields += field.getName() + ",";
                        }
                        outputMethods += " ] ,";
                        outputFields += " ] }";

                        outputLine = HttpBuilder.httpJSONResponse(outputMethods + outputFields);
                    } else if (command.startsWith("invoke")) {
                        String request = command.split("invoke")[1];
                        System.out.println("REQUEST: " + request);
                        String className = request.split(",")[0];
                        String methodName = request.split(",")[1];
                        className = className.substring(1, className.length());
                        methodName = methodName.substring(0, methodName.length() - 1);
                        System.out.println(className);
                        System.out.println(methodName);
                        Class<?> classToInvoke = Class.forName(className);
                        Class[] parameters = new Class[]{};
                        Method methodToCall = classToInvoke.getMethod(methodName, parameters);
                        methodToCall.invoke(null);
                        System.out.println("Invocación Correcta");
                        outputLine = HttpBuilder.httpJSONResponse("{\"Invocación\": \"Correcta\"}");

                    }else if(command.startsWith("unaryInvoke")){
                        String request = command.split("unaryInvoke")[1];
                        System.out.println("REQUEST: " + request);
                        String className = request.split(",")[0];
                        String methodName = request.split(",")[1];
                        String type = request.split(",")[2];
                        String value = request.split(",")[3];
                        value = value.substring(0, value.length()-1);
                        className = className.substring(1, className.length());
                        System.out.println(className);
                        System.out.println(methodName);
                        System.out.println(type);
                        System.out.println(value);

                        Class<?> classToInvoke = Class.forName(className);
                        Class<?> classType;
                        if(type.equals("int")){
                            classType = int.class;
                        } else if (type.equals("String")) {
                            classType = String.class;
                        } else {
                            classType = Class.forName(type);
                        }
                        Class[] parameters = new Class[]{classType};
                        Method methodToCall = classToInvoke.getMethod(methodName, parameters);
                        Object result = methodToCall.invoke(null, Integer.parseInt(value));
                        System.out.println("Invocación Correcta");
                        outputLine = HttpBuilder.httpJSONResponse("{\"Resultado\": \""+ result + "\"}");
                    }else{
                        outputLine = "{\"Probando Api\": \"Hola\"}";
                    }
                }
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
}

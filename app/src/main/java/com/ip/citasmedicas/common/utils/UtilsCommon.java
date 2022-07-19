package com.ip.citasmedicas.common.utils;

public class UtilsCommon {

    /*
     *   Codificar un correo electrónico
     * */
    public static String getEmailEncoded(String email){
        String preKey = email.replace("_", "__");
        return preKey.replace(".", "_");
    }

    public static String getEmailToTopic(String email){
        String topic = getEmailEncoded(email);
        topic = topic.replace("@", "_64");

        return topic;
    }


}
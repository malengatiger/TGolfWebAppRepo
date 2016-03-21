/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

/**
 *
 * @author aubreyM
 */
public class ServerStatus {

    public static final int OK = 0,
            ERROR_GCM = 11,
            ERROR_DATABASE = 22,
            ERROR_UNKNOWN_REQUEST = 33,
            ERROR_SERVER = 44,
            ERROR_SERVICES = 55,
            ERROR_JSON_SYNTAX = 66,
            ERROR_WEBSOCKET = 77,
            ERROR_DATA_COMPRESSION = 88,
            ERROR_LOGGING_IN = 99,
            ERROR_DUPLICATE_DATA = 110;

    
    public static String getMessage(int statusCode) {
        String message = "No known message";

        switch (statusCode) {
            case OK:
                message = "Request processed OK";
                break;
            case ERROR_GCM:
                message = "Sorry! Error processing Cloud Messaging request";
                break;
            case ERROR_DATABASE:
                message = "Sorry! Data related error on cloud Server";
                break;
            case ERROR_UNKNOWN_REQUEST:
                message = "Request unknown. Ignored.";
                break;
            case ERROR_SERVER:
                message = "Sorry! Error encountered on cloud server. Please try again.";
                break;
            case ERROR_JSON_SYNTAX:
                message = "Sorry! JSON syntax error. Support department is taking care of it as we speak!";
                break;
            case ERROR_WEBSOCKET:
                message = "Sorry! WebSocket error. Support department is taking care of it as we speak!";
                break;
            case ERROR_DATA_COMPRESSION:
                message = "Sorry! Unable to compress response data. Support department is taking care of it as we speak!";
                break;
            case ERROR_LOGGING_IN:
                message = "Email address or PIN are invalid. Please try again.";
                break;
            case ERROR_DUPLICATE_DATA:
                message = "Data received may be  aduplicate of existing data. Please try again.";
                break;
        }

        return message;

    }
}

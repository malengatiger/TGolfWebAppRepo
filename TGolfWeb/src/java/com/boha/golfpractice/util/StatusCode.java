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
public class StatusCode {
    
    public static final int OK = 0,
            ERROR_GCM = 33,
            ERROR_DATABASE = 99,
            ERROR_SERVER = 88,
            ERROR_SERVICES = 77,
            ERROR_JSON_SYNTAX = 79,
            ERROR_WEBSOCKET = 81,
            TASK_STATUS_ADDED = 105,
            PROJECT_STATUS_ADDED = 100,
            PHOTO_ADDED = 101,
            MONITOR_REGISTERED = 102,
            COMPANY_REGISTERED = 103,
            STAFF_REGISTERED = 104,
            DEVICE_REGISTERED = 105,
            ERROR_DATA_COMPRESSION = 110,
            ERROR_SIGN_IN = 111,
            ERROR_REGISTRATION = 112;
    
    public static final String OK_MESSAGE = "OK",
            ERROR_SIGN_IN_MSG = "Email account not found",
            ERROR_REGISTRATION_MSG = "Email already exists",
            ERROR_DATABASE_MESSAGE = "Database related error",
            ERROR_SERVER_MESSAGE = "Unexpected Server Error",
            ERROR_SERVICES_MESSAGE = "Error communicating with external service";
}

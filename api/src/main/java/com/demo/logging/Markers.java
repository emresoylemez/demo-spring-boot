package com.demo.logging;

import org.slf4j.Marker;

import static org.slf4j.MarkerFactory.getMarker;

public class Markers {
    public static final Marker INVALID_TOKEN = getMarker("INVALID_TOKEN");
    public static final Marker EXIT = getMarker("EXIT");
    public static final Marker ENTER = getMarker("ENTER");
    public static final Marker EXCEPTION = getMarker("EXCEPTION");
    public static final Marker REQUEST = getMarker("REQUEST");
    public static final Marker RESPONSE = getMarker("RESPONSE");
    public static final Marker REQUEST_RECEIVED = getMarker("REQUEST_RECEIVED");
    public static final Marker REQUEST_COMPLETED = getMarker("REQUEST_COMPLETED");
    public static final Marker REQUEST_COMPLETED_WITH_EXCEPTION = getMarker("REQUEST_COMPLETED_WITH_EXCEPTION");
}

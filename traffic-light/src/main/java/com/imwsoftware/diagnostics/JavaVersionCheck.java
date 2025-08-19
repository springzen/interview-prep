package com.imwsoftware.diagnostics;

public class JavaVersionCheck {
    public static void main(String[] args) {
        System.out.println("🧠 Runtime Java version: " + System.getProperty("java.version"));
        System.out.println("⚙️  Java vendor: " + System.getProperty("java.vendor"));
        System.out.println("📦 Java home: " + System.getProperty("java.home"));
    }
}
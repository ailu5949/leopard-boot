package com.company.example.web;

import java.io.IOException;

import io.leopard.boot.jetty.JettyServer;

public class JettyTest {

	public static void main(String[] args) throws IOException {
		JettyServer.run(80);
	}

}
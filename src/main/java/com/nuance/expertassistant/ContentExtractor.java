/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek_rohatgi
 */
public class ContentExtractor {

	static PrintWriter writer = null;

	public static void startDocument(String Title, String Filepath) {
		try {
			writer = new PrintWriter(Filepath, "UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<document xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"\n"
					+ "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n"
					+ "          xmlns:fn=\"http://www.w3.org/2005/xpath-functions\"\n"
					+ "          xmlns:xdt=\"http://www.w3.org/2005/xpath-datatypes\"\n"
					+ "          docid=\"a382247e\"\n" + "          title=\""
					+ Title + "\">");
		} catch (final FileNotFoundException ex) {
			Logger.getLogger(ContentExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (final UnsupportedEncodingException ex) {
			Logger.getLogger(ContentExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	public static void extract(String URL) {
		try {
			final Document doc = Jsoup.connect(URL).timeout(0).get();
			extract(doc);
		} catch (final IOException ex) {
			Logger.getLogger(ContentExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	public static void extract(File file, String charsetName) {
		try {
			final Document doc = Jsoup.parse(file, charsetName);
			extract(doc);
		} catch (final IOException ex) {
			Logger.getLogger(ContentExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	public static void extract(Document doc) {

		final Elements links = doc.getElementsByTag("a");
		final Elements ps = doc.select("p");

		final String title = doc.title();

		print("<section id =\"{}\" title =\""
				+ stripNonValidXMLCharacters(doc.title()) + "\">");

		final Elements elements = doc.select("*");

		final ArrayList<String> openHeaderList = new ArrayList<String>();

		for (final Element element : elements) {
			if (element.ownText() == null || element.ownText().isEmpty()
					|| element.ownText().trim() == "") {

			} else if (element.tagName().toString().contains("a")) {

			} else if (element.tagName().contains("h1")
					&& element.text() != null && !element.text().isEmpty()) {

				if (openHeaderList.contains("h1")) {
					openHeaderList.remove("h1");
					print("</section>");
				}
				if (openHeaderList.contains("h2")) {
					openHeaderList.remove("h2");
					print("</section>");
				}
				if (openHeaderList.contains("h3")) {
					openHeaderList.remove("h3");
					print("</section>");
				}
				if (openHeaderList.contains("h4")) {
					openHeaderList.remove("h4");
					print("</section>");
				}

				print("<section id =\"{}\" title =\""
						+ stripNonValidXMLCharacters(element.text()) + "\">");
				openHeaderList.add("h1");

			} else if (element.tagName().contains("h2")
					&& element.text() != null && !element.text().isEmpty()) {

				if (openHeaderList.contains("h2")) {
					openHeaderList.remove("h2");
					print("</section>");
				}
				if (openHeaderList.contains("h3")) {
					openHeaderList.remove("h3");
					print("</section>");
				}
				if (openHeaderList.contains("h4")) {
					openHeaderList.remove("h4");
					print("</section>");
				}

				print("<section id =\"{}\" title =\""
						+ stripNonValidXMLCharacters(element.text()) + "\">");
				openHeaderList.add("h2");

			} else if (element.tagName().contains("h3")
					&& element.text() != null && !element.text().isEmpty()) {

				if (openHeaderList.contains("h3")) {
					openHeaderList.remove("h3");
					print("</section>");
				}
				if (openHeaderList.contains("h4")) {
					openHeaderList.remove("h4");
					print("</section>");
				}

				print("<section id =\"{}\" title =\""
						+ stripNonValidXMLCharacters(element.text()) + "\">");
				openHeaderList.add("h3");

			} else if (element.tagName().contains("h4")
					&& element.text() != null && !element.text().isEmpty()) {

				if (openHeaderList.contains("h4")) {
					openHeaderList.remove("h4");
					print("</section>");
				}

				print("<section id =\"{}\" title =\""
						+ stripNonValidXMLCharacters(element.text()) + "\">");
				openHeaderList.add("h4");

			}

			else {
				print("<para>");
				print(stripNonValidXMLCharacters(element.ownText()));
				print("</para>");
			}

			/*
			 * if (element.tagName().contains("img")) { print("<img src=\"" +
			 * element.attr("src") + "\"></img>"); }
			 */
		}

		if (openHeaderList.contains("h1")) {
			openHeaderList.remove("h1");
			print("</section>");
		}
		if (openHeaderList.contains("h2")) {
			openHeaderList.remove("h2");
			print("</section>");
		}
		if (openHeaderList.contains("h3")) {
			openHeaderList.remove("h3");
			print("</section>");
		}
		if (openHeaderList.contains("h4")) {
			openHeaderList.remove("h4");
			print("</section>");
		}

		print("</section>");

	}

	private static synchronized void print(String msg) {

		try {
			writer.println(msg);
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Exception cause while writing to file");
		}
	}

	public static void endDocument() {
		writer.println("</document>");
		writer.close();
	}

	public static String stripNonValidXMLCharacters(String in) {

		String output = in.replaceAll(">>", "");

		output = output.replaceAll(">>", "").replaceAll("\n", "")
				.replaceAll("\"", "").replaceAll("\u201c", "")
				.replaceAll("\u201d", "").replaceAll("\u2022", "")
				.replaceAll("&", "");

		return StringEscapeUtils.escapeXml(output);

		/*
		 * StringBuffer out = new StringBuffer(); // Used to hold the output.
		 * char current; // Used to reference the current character.
		 *
		 * if (in == null || in.equalsIgnoreCase("")) { return null; }
		 *
		 * for (int i = 0; i < in.length(); i++) { current = in.charAt(i); //
		 * NOTE: No IndexOutOfBoundsException caught here; it should not happen.
		 * if ((current == 0x9) || (current == 0xA) || (current == 0xD) ||
		 * ((current >= 0x20) && (current <= 0xD7FF)) || ((current >= 0xE000) &&
		 * (current <= 0xFFFD)) || ((current >= 0x10000) && (current <=
		 * 0x10FFFF))) { out.append(current); } }
		 *
		 * return out.toString().replaceAll(">>", "");
		 */

	}

}

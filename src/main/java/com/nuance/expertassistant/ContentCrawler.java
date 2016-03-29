/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import static com.nuance.expertassistant.ContentCrawlerOptions.getInput;
import static com.nuance.expertassistant.ContentCrawlerOptions.getOutput;
import static java.lang.System.exit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek_rohatgi
 */
public class ContentCrawler {

	static ArrayList<String> visitedURLs = new ArrayList<String>();
	static String URLCrawlPattern = null;
	static int PageLimit = 100;

	public static void main(String args[]) {
		if (args.length == 0) {
			ContentExtractor.startDocument("Test3", "/Users/abhishek_rohatgi/"
					+ "Test3" + ".xml");
			ContentExtractor
					.extract("http://www.audihelp.com/auda-147-tyre_repairs.html");
			ContentExtractor.endDocument();
		} else {
			final ContentCrawlerOptions options = new ContentCrawlerOptions(
					args);
			final ContentCrawlerInputTypes inputType = ContentCrawlerOptions
					.getInputType();
			if (ContentCrawlerInputTypes.FILE.equals(inputType)) {
				final File outputFile = new File(getOutput());
				if (!outputFile.getParentFile().exists()) {
					outputFile.getParentFile().mkdirs();
				}
				translateFile(getInput(), getOutput());
			} else if (ContentCrawlerInputTypes.FOLDER.equals(inputType)) {
				final File outputFolder = new File(getOutput());
				final Collection<File> inputFiles = FileUtils.listFiles(
						new File(getInput()), new RegexFileFilter(
								"^(.*\\.(html)?)"),
						DirectoryFileFilter.DIRECTORY);
				for (final File inputFile : inputFiles) {
					final String outputFileName = inputFile.getAbsolutePath()
							.substring(getInput().length()) + ".xml";
					final File outputFile = new File(outputFolder,
							outputFileName);
					if (!outputFile.getParentFile().exists()) {
						outputFile.getParentFile().mkdirs();
					}
					translateFile(inputFile.getAbsolutePath(),
							outputFile.getAbsolutePath());
				}

			} else {
				ContentExtractor.startDocument(getInput(), getOutput());
				ContentExtractor.extract(getInput());
				ContentExtractor.endDocument();
			}
		}
	}

	public static void translateFile(String input, String output) {
		final File inputFile = new File(input);
		final String fileName = inputFile.getName();
		final String title = fileName.substring(0, fileName.lastIndexOf('.'));

		ContentExtractor.startDocument(title, output);
		ContentExtractor.extract(inputFile, "UTF-8");
		ContentExtractor.endDocument();
	}

	public static void crawlWebSite(String url, String depth, String pattern,
			String limit, String title) {
		System.out.println(" The url is :" + url);
		System.out.println(" The depth is :" + depth);
		System.out.println(" The pattern is :" + pattern);
		System.out.println(" The limit is :" + limit);
		System.out.println(" The title is :" + title);

		ContentExtractor.startDocument(title, "/Users/abhishek_rohatgi/"
				+ title + ".xml");
		URLCrawlPattern = pattern;
		PageLimit = Integer.parseInt(limit);
		crawl(url, Integer.parseInt(depth));
		ContentExtractor.endDocument();
	}

	/*
	 * public static void main(String args[]) {
	 * ContentExtractor.startDocument("bestbuy",
	 * "/Users/abhishek_rohatgi/bestbuy.xml"); URLCrawlPattern =
	 * "http://www.bestbuy.ca/en-CA/";
	 * crawl("http://www.bestbuy.ca/en-CA/home.aspx", 1);
	 * ContentExtractor.endDocument(); }
	 */

	public static void crawl(String URL, int maxdepth) {

		final ArrayList<String> URLList1 = new ArrayList<String>();
		URLList1.addAll(listURLs(URL, 1));

		if (maxdepth == 1) {
			return;
		}

		final ArrayList<String> URLList2 = new ArrayList<String>();

		for (int i = 0; i < URLList1.size(); i++) {
			URLList2.addAll(listURLs(URLList1.get(i), 2));
		}

		if (maxdepth == 2) {
			return;
		}

		final ArrayList<String> URLList3 = new ArrayList<String>();

		for (int i = 0; i < URLList2.size(); i++) {
			URLList3.addAll(listURLs(URLList2.get(i), 3));
		}

		if (maxdepth == 3) {
			return;
		}

		final ArrayList<String> URLList4 = new ArrayList<String>();

		for (int i = 0; i < URLList3.size(); i++) {
			URLList4.addAll(listURLs(URLList3.get(i), 4));
		}

		if (maxdepth == 4) {
			return;
		}

		final ArrayList<String> URLList5 = new ArrayList<String>();

		for (int i = 0; i < URLList4.size(); i++) {
			URLList5.addAll(listURLs(URLList4.get(i), 5));
		}

		if (maxdepth == 5) {
			return;
		}

		return;

	}

	public static ArrayList<String> listURLs(String StartUrl, int depth) {

		System.out.println(" Current Depth is : [" + depth + "]");
		// System.out.println(" PARENT URL is : [" + StartUrl + "]");
		// System.out.println(" URL CRAWL Pattern  is : [" + URLCrawlPattern +
		// "]");

		final ArrayList<String> tempURLs = new ArrayList<String>();

		try {
			final Document doc = Jsoup.connect(StartUrl).timeout(0).get();
			final Elements links = doc.select("a");

			for (final Element link : links) {
				final String absLink = link.attr("abs:href");
				if (!visitedURLs.contains(absLink)
						&& absLink.contains(URLCrawlPattern)) {
					visitedURLs.add(absLink);
					if (visitedURLs.size() > PageLimit) {
						ContentExtractor.endDocument();
						System.out
						.println(" Max URL Limit Reached - [Stopping ....] ");
						System.out.println(" [Stopped] ");

						exit(0);
					}
					tempURLs.add(absLink);
					System.out.println(" URLs Extracted So Far : ["
							+ visitedURLs.size() + "]");
					System.out.println(" Extracting Content From : [" + absLink
							+ "]");
					ContentExtractor.extract(absLink);
				}

			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return tempURLs;

	}

	/*
	 *
	 * public static void crawl(String URL, int depth) {
	 *
	 * if(depth == 0) return;
	 *
	 * System.out.println("Crawling at Depth : " + depth);
	 * System.out.println("Crawling URL : " + URL);
	 *
	 * // ContentExtractor.extract(URL);
	 *
	 * try { Document doc = Jsoup.connect(URL).timeout(0).get(); Elements links
	 * = doc.getElementsByTag("a");
	 *
	 * for (Element link : links) { String linkHref = link.attr("abs:href");
	 * crawl(linkHref,depth-1); System.out.println(" URL Found : " + URL);
	 *
	 * }
	 *
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
}

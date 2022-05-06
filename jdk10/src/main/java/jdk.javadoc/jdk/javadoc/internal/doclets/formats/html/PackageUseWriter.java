/*
 * Copyright (c) 1998, 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package jdk.javadoc.internal.doclets.formats.html;

import jdk.javadoc.internal.doclets.formats.html.markup.Table;
import jdk.javadoc.internal.doclets.formats.html.markup.TableHeader;

import java.util.*;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import jdk.javadoc.internal.doclets.formats.html.markup.ContentBuilder;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlConstants;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTag;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTree;
import jdk.javadoc.internal.doclets.formats.html.markup.Links;
import jdk.javadoc.internal.doclets.formats.html.markup.StringContent;
import jdk.javadoc.internal.doclets.toolkit.Content;
import jdk.javadoc.internal.doclets.toolkit.util.ClassUseMapper;
import jdk.javadoc.internal.doclets.toolkit.util.DocFileIOException;
import jdk.javadoc.internal.doclets.toolkit.util.DocPath;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;

/**
 * Generate package usage information.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 * @author Robert G. Field
 * @author Bhavesh Patel (Modified)
 */
public class PackageUseWriter extends SubWriterHolderWriter {

    final PackageElement packageElement;
    final SortedMap<String, Set<TypeElement>> usingPackageToUsedClasses = new TreeMap<>();
    protected HtmlTree mainTree = HtmlTree.MAIN();
    final String packageUseTableSummary;

    /**
     * Constructor.
     *
     * @param configuration the configuration
     * @param mapper a mapper to provide details of where elements are used
     * @param filename the file to be generated
     * @param pkgElement the package element to be documented
     */
    public PackageUseWriter(HtmlConfiguration configuration,
                            ClassUseMapper mapper, DocPath filename,
                            PackageElement pkgElement) {
        super(configuration, DocPath.forPackage(pkgElement).resolve(filename));
        this.packageElement = pkgElement;

        // by examining all classes in this package, find what packages
        // use these classes - produce a map between using package and
        // used classes.
        for (TypeElement usedClass : utils.getEnclosedTypeElements(pkgElement)) {
            Set<TypeElement> usingClasses = mapper.classToClass.get(usedClass);
            if (usingClasses != null) {
                for (TypeElement usingClass : usingClasses) {
                    PackageElement usingPackage = utils.containingPackage(usingClass);
                    Set<TypeElement> usedClasses = usingPackageToUsedClasses
                            .get(utils.getPackageName(usingPackage));
                    if (usedClasses == null) {
                        usedClasses = new TreeSet<>(utils.makeGeneralPurposeComparator());
                        usingPackageToUsedClasses.put(utils.getPackageName(usingPackage),
                                                      usedClasses);
                    }
                    usedClasses.add(usedClass);
                }
            }
        }

        packageUseTableSummary = resources.getText("doclet.Use_Table_Summary",
                resources.getText("doclet.packages"));
    }

    /**
     * Generate a class page.
     *
     * @param configuration the current configuration of the doclet.
     * @param mapper        the mapping of the class usage.
     * @param pkgElement    the package being documented.
     * @throws DocFileIOException if there is a problem generating the package use page
     */
    public static void generate(HtmlConfiguration configuration,
                                ClassUseMapper mapper, PackageElement pkgElement)
            throws DocFileIOException {
        DocPath filename = DocPaths.PACKAGE_USE;
        PackageUseWriter pkgusegen = new PackageUseWriter(configuration, mapper, filename, pkgElement);
        pkgusegen.generatePackageUseFile();
    }

    /**
     * Generate the package use list.
     * @throws DocFileIOException if there is a problem generating the package use page
     */
    protected void generatePackageUseFile() throws DocFileIOException {
        HtmlTree body = getPackageUseHeader();
        HtmlTree div = new HtmlTree(HtmlTag.DIV);
        div.setStyle(HtmlStyle.contentContainer);
        if (usingPackageToUsedClasses.isEmpty()) {
            div.addContent(contents.getContent("doclet.ClassUse_No.usage.of.0", utils.getPackageName(packageElement)));
        } else {
            addPackageUse(div);
        }
        if (configuration.allowTag(HtmlTag.MAIN)) {
            mainTree.addContent(div);
            body.addContent(mainTree);
        } else {
            body.addContent(div);
        }
        HtmlTree tree = (configuration.allowTag(HtmlTag.FOOTER))
                ? HtmlTree.FOOTER()
                : body;
        addNavLinks(false, tree);
        addBottom(tree);
        if (configuration.allowTag(HtmlTag.FOOTER)) {
            body.addContent(tree);
        }
        printHtmlDocument(null, true, body);
    }

    /**
     * Add the package use information.
     *
     * @param contentTree the content tree to which the package use information will be added
     */
    protected void addPackageUse(Content contentTree) {
        HtmlTree ul = new HtmlTree(HtmlTag.UL);
        ul.setStyle(HtmlStyle.blockList);
        if (configuration.packages.size() > 1) {
            addPackageList(ul);
        }
        addClassList(ul);
        contentTree.addContent(ul);
    }

    /**
     * Add the list of packages that use the given package.
     *
     * @param contentTree the content tree to which the package list will be added
     */
    protected void addPackageList(Content contentTree) {
        Content caption = contents.getContent(
                "doclet.ClassUse_Packages.that.use.0",
                getPackageLink(packageElement, utils.getPackageName(packageElement)));
        Table table = new Table(configuration.htmlVersion, HtmlStyle.useSummary)
                .setSummary(packageUseTableSummary)
                .setCaption(caption)
                .setHeader(getPackageTableHeader())
                .setColumnStyles(HtmlStyle.colFirst, HtmlStyle.colLast);
        for (String pkgname: usingPackageToUsedClasses.keySet()) {
            PackageElement pkg = utils.elementUtils.getPackageElement(pkgname);
            Content packageLink = links.createLink(utils.getPackageName(pkg),
                    new StringContent(utils.getPackageName(pkg)));
            Content summary = new ContentBuilder();
            if (pkg != null && !pkg.isUnnamed()) {
                addSummaryComment(pkg, summary);
            } else {
                summary.addContent(Contents.SPACE);
            }
            table.addRow(packageLink, summary);
        }
        Content li = HtmlTree.LI(HtmlStyle.blockList, table.toContent());
        contentTree.addContent(li);
    }

    /**
     * Add the list of classes that use the given package.
     *
     * @param contentTree the content tree to which the class list will be added
     */
    protected void addClassList(Content contentTree) {
        TableHeader classTableHeader = new TableHeader(
                contents.classLabel, contents.descriptionLabel);
        for (String packageName : usingPackageToUsedClasses.keySet()) {
            PackageElement usingPackage = utils.elementUtils.getPackageElement(packageName);
            HtmlTree li = new HtmlTree(HtmlTag.LI);
            li.setStyle(HtmlStyle.blockList);
            if (usingPackage != null) {
                li.addContent(links.createAnchor(utils.getPackageName(usingPackage)));
            }
            String tableSummary = resources.getText("doclet.Use_Table_Summary",
                                                        resources.getText("doclet.classes"));
            Content caption = contents.getContent(
                    "doclet.ClassUse_Classes.in.0.used.by.1",
                    getPackageLink(packageElement, utils.getPackageName(packageElement)),
                    getPackageLink(usingPackage, utils.getPackageName(usingPackage)));
            Table table = new Table(configuration.htmlVersion, HtmlStyle.useSummary)
                    .setSummary(tableSummary)
                    .setCaption(caption)
                    .setHeader(classTableHeader)
                    .setColumnStyles(HtmlStyle.colFirst, HtmlStyle.colLast);
            for (TypeElement te : usingPackageToUsedClasses.get(packageName)) {
                DocPath dp = pathString(te,
                        DocPaths.CLASS_USE.resolve(DocPath.forName(utils, te)));
                Content stringContent = new StringContent(utils.getSimpleName(te));
                Content typeContent = Links.createLink(dp.fragment(getPackageAnchorName(usingPackage)),
                        stringContent);
                Content summary = new ContentBuilder();
                addIndexComment(te, summary);

                table.addRow(typeContent, summary);
            }
            li.addContent(table.toContent());
            contentTree.addContent(li);
        }
    }

    /**
     * Get the header for the package use listing.
     *
     * @return a content tree representing the package use header
     */
    private HtmlTree getPackageUseHeader() {
        String packageText = resources.getText("doclet.Package");
        String name = packageElement.isUnnamed() ? "" : utils.getPackageName(packageElement);
        String title = resources.getText("doclet.Window_ClassUse_Header", packageText, name);
        HtmlTree bodyTree = getBody(true, getWindowTitle(title));
        HtmlTree htmlTree = (configuration.allowTag(HtmlTag.HEADER))
                ? HtmlTree.HEADER()
                : bodyTree;
        addTop(htmlTree);
        addNavLinks(true, htmlTree);
        if (configuration.allowTag(HtmlTag.HEADER)) {
            bodyTree.addContent(htmlTree);
        }
        ContentBuilder headContent = new ContentBuilder();
        headContent.addContent(contents.getContent("doclet.ClassUse_Title", packageText));
        headContent.addContent(new HtmlTree(HtmlTag.BR));
        headContent.addContent(name);
        Content heading = HtmlTree.HEADING(HtmlConstants.TITLE_HEADING, true,
                HtmlStyle.title, headContent);
        Content div = HtmlTree.DIV(HtmlStyle.header, heading);
        if (configuration.allowTag(HtmlTag.MAIN)) {
            mainTree.addContent(div);
        } else {
            bodyTree.addContent(div);
        }
        return bodyTree;
    }

    /**
     * Get the module link.
     *
     * @return a content tree for the module link
     */
    @Override
    protected Content getNavLinkModule() {
        Content linkContent = getModuleLink(utils.elementUtils.getModuleOf(packageElement),
                contents.moduleLabel);
        Content li = HtmlTree.LI(linkContent);
        return li;
    }

    /**
     * Get this package link.
     *
     * @return a content tree for the package link
     */
    @Override
    protected Content getNavLinkPackage() {
        Content linkContent = Links.createLink(DocPaths.PACKAGE_SUMMARY,
                contents.packageLabel);
        Content li = HtmlTree.LI(linkContent);
        return li;
    }

    /**
     * Get the use link.
     *
     * @return a content tree for the use link
     */
    @Override
    protected Content getNavLinkClassUse() {
        Content li = HtmlTree.LI(HtmlStyle.navBarCell1Rev, contents.useLabel);
        return li;
    }

    /**
     * Get the tree link.
     *
     * @return a content tree for the tree link
     */
    @Override
    protected Content getNavLinkTree() {
        Content linkContent = Links.createLink(DocPaths.PACKAGE_TREE,
                contents.treeLabel);
        Content li = HtmlTree.LI(linkContent);
        return li;
    }
}

package com.axreng.backend.controllers;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LinkServiceTest {
    private String htmlPage;

    @BeforeEach
    void setUp() {
        this.htmlPage = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><link type=\"text/css\" rel=\"stylesheet\" id=\"dark-mode-custom-link\"><link type=\"text/css\" rel=\"stylesheet\" id=\"dark-mode-general-link\"><style lang=\"en\" type=\"text/css\" id=\"dark-mode-custom-style\"></style><style lang=\"en\" type=\"text/css\" id=\"dark-mode-native-style\"></style><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Linux manual pages</title><link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet/manpages.css\"><meta name=\"generator\" content=\"DocBook XSL Stylesheets Vsnapshot\"><link rel=\"home\" href=\"index.html\" title=\"Linux manual pages\"><link rel=\"next\" href=\"manpageindex.html\" title=\"Manual page section index\"><script type=\"text/javascript\" src=\"stylesheet/manpages.js\"></script><style type=\"text/css\" id=\"operaUserStyle\"></style><link rel=\"icon\" href=\"stylesheet/icon.gif\" type=\"image/gif\"></head><body onload=\"javascript:init()\"><div class=\"navheader\"><table width=\"100%\" summary=\"Navigation header\"><tbody><tr><th colspan=\"3\" align=\"center\">Linux manual pages</th></tr><tr><td width=\"20%\" align=\"left\">&nbsp;</td><th width=\"60%\" align=\"center\">&nbsp;</th><td width=\"20%\" align=\"right\">&nbsp;<a accesskey=\"n\" href=\"manpageindex.html\">Next</a></td></tr></tbody></table><hr></div><div class=\"part\"><div class=\"titlepage\"><div><div><h1 class=\"title\"><a id=\"index\"></a>Linux manual pages</h1></div><div><p class=\"copyright\">Copyright © 2007-2020 Sam Varshavchik</p></div><div><div class=\"legalnotice\"><a id=\"idm45422340552864\"></a><p>\n" +
                "\tThis is a compilation of Linux manual pages, converted to HTML.\n" +
                "\tPermission is granted to copy, distribute and/or modify this\n" +
                "\tcompilation of Linux manual pages under\n" +
                "\tthe terms of the GNU Free Documentation License, Version 1.2;\n" +
                "\twith the Invariant Sections being the license and copyright sections\n" +
                "\tof each individual manual page,\n" +
                "\tno Front-Cover texts, and no Back-Cover texts.\n" +
                "\tA copy of the license is included in the section entitled\n" +
                "\t<span class=\"quote\">“<span class=\"quote\"><a class=\"literalurl\" href=\"gfdl.html\" title=\"Appendix&nbsp;A.&nbsp;GNU Free Documentation License\">GNU Free Documentation\n" +
                "\t    License</a></span>”</span>.</p><p>\n" +
                "\tNote that individual manual pages have different authors and distribution\n" +
                "\tterms.  This notice applies to this compilation of manual pages, as a whole.\n" +
                "\tIndividual manual pages may be redistributed as per their individual\n" +
                "\tdistribution terms.</p></div></div></div></div><div class=\"partintro\"><div><div><div><h1 class=\"title\"><a id=\"intro\"></a>Introduction</h1></div></div></div><p>\n" +
                "      This compilation\n" +
                "      does not contain all manual pages on a typical Linux installation.\n" +
                "      Linux manual pages come from thousands of individual software packages,\n" +
                "      with each package typically installing one or two pages, each.\n" +
                "      This compilation includes core manual pages selected from the following\n" +
                "      sources:</p><div class=\"itemizedlist\"><ul class=\"itemizedlist\" style=\"list-style-type: disc; \"><li class=\"listitem\">\n" +
                "\t  <a class=\"ulink\" href=\"https://www.kernel.org/pub/linux/docs/man-pages/\" target=\"_top\"><span class=\"package\">man-pages-5.04</span></a>,\n" +
                "\t  Linux core manual pages maintained by Michael Kerrisk</li><li class=\"listitem\">\n" +
                "\t  <a class=\"ulink\" href=\"https://www.gnu.org/software/coreutils/\" target=\"_top\"><span class=\"package\">coreutils-8.31</span></a></li><li class=\"listitem\">\n" +
                "\t  <a class=\"ulink\" href=\"https://www.kernel.org/pub/linux/utils/util-linux/\" target=\"_top\"><span class=\"package\">util-linux-2.33.1</span></a></li><li class=\"listitem\">\n" +
                "\t  <a class=\"ulink\" href=\"http://www.openldap.org\" target=\"_top\"><span class=\"package\">OpenLDAP</span> 2.4.48</a></li><li class=\"listitem\">\n" +
                "\t  <a class=\"ulink\" href=\"http://www.linux-pam.org/library/\" target=\"_top\"><span class=\"package\">Linux-PAM</span> 1.3.0</a></li><li class=\"listitem\">\n" +
                "\t  <a class=\"ulink\" href=\"http://www.PCRE.org/\" target=\"_top\"><span class=\"package\">PCRE</span> 10.34</a></li></ul></div><p>\n" +
                "      You can\n" +
                "      <a class=\"literalurl\" href=\"manpageindex.html\" title=\"Manual page section index\">browse this compilation of\n" +
                "\tLinux manual pages online</a>, or download and peruse it at your leisure,\n" +
                "\tby downloading an <a class=\"ulink\" href=\"archive\" target=\"_top\">archive</a>.\n" +
                "    </p><p>\n" +
                "      In all cases, please grab\n" +
                "      (<a class=\"ulink\" href=\"http://www.courier-mta.org/KEYS.bin\" target=\"_top\">my GPG key</a>),\n" +
                "      and keep it handy.</p><div class=\"section\"><div class=\"titlepage\"><div><div><h2 class=\"title\" style=\"clear: both\"><a id=\"feedback\"></a>Feedback</h2></div></div></div><p>\n" +
                "\tYou should confirm the source of any errors in this documentation, before\n" +
                "\treporting them.\n" +
                "\tLinux manual pages are a collective effort of thousands of individuals.\n" +
                "\tIn most cases, this compilation does not correct errors that are present\n" +
                "\tin the original manual page text.</p><p>\n" +
                "\tBefore reporting any errors to me,\n" +
                "\t&lt;<span class=\"emphasis\"><em>mrsam@</em></span><span class=\"emphasis\"><em>courier-mta.com</em></span>&gt;,\n" +
                "\tcheck if the error exists is the same manual page in the\n" +
                "\t<span class=\"emphasis\"><em>same version</em></span> of the original package.\n" +
                "\tIf not, the error is mine.  I probably introduced it when I converted the\n" +
                "\tmanual page from its original troff source to <acronym class=\"acronym\">HTML</acronym>.</p><p>\n" +
                "\tBut if the error also exists in the original manual page, notify the manual\n" +
                "\tpage's original author, unless a newer version of the original package is\n" +
                "\tavailable, and the error is already fixed in the newer version.\n" +
                "\tIf the newer version fixes the error, the fix will be included when I\n" +
                "\tprepare a new version of this manual page compilation.</p></div></div></div><div class=\"navfooter\"><hr><table width=\"100%\" summary=\"Navigation footer\"><tbody><tr><td width=\"40%\" align=\"left\">&nbsp;</td><td width=\"20%\" align=\"center\">&nbsp;</td><td width=\"40%\" align=\"right\">&nbsp;<a accesskey=\"n\" href=\"manpageindex.html\">Next</a></td></tr><tr><td width=\"40%\" align=\"left\" valign=\"top\">&nbsp;</td><td width=\"20%\" align=\"center\">&nbsp;</td><td width=\"40%\" align=\"right\" valign=\"top\">&nbsp;Manual page section index</td></tr></tbody></table></div></body></html>";
    }
    @Test
    void givenBaseUrl_whenGetLinksReturnThreeValidLinks_thenCorrect() {
        LinkService service = new LinkService("http://hiring.axreng.com/");
        Set<String> links = service.getLinks(htmlPage);

        MatcherAssert.assertThat(links, Matchers.contains("http://hiring.axreng.com/gfdl.html", "http://hiring.axreng.com/manpageindex.html", "http://hiring.axreng.com/archive"));
    }
}
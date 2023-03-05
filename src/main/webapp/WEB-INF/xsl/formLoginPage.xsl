<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<xsl:import href="/import.xsl"/>

	<xsl:template match="/">
		<xsl:text disable-output-escaping="yes">&#60;!DOCTYPE HTML&#62;</xsl:text>
		<HTML dir="ltr" lang="zh-TW">
			<xsl:apply-templates/>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<HEAD>
			<META charset="UTF-8"/>
			<META name="viewport" content="width=device-width, initial-scale=1.0"/>
			<!--<LINK href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/dark-hive/jquery-ui.css" rel="stylesheet" media="all" type="text/css"/>-->
			<LINK href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/font-awesome.min.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/common.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/formLoginPage.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"/>
			<SCRIPT src="/SCRIPT/gcse.js"/>
			<SCRIPT src="/SCRIPT/formLoginPage.js"/>
			<TITLE>旅图中国跨境电商 &#187; 店家登入</TITLE>
		</HEAD>
		<xsl:comment>
			<xsl:value-of select="system-property('xsl:version')"/>
		</xsl:comment>
		<BODY>
			<DIV>
				<DIV>
					<HEADER>
						<DIV>
							<xsl:call-template name="header"/>
						</DIV>
					</HEADER>
				</DIV>
				<DIV>
					<DIV>
						<NAV>
							<xsl:call-template name="navigator"/>
							<DIV>
								<FORM action="j_security_check" method="POST">
									<BR/>
									<DIV>旅图中国跨境电商 &#187; 店家登入</DIV>
									<BR/>
									<TABLE class="form">
										<TBODY>
											<TR>
												<TH class="must">
													<LABEL for="j_username">帐号</LABEL>
												</TH>
												<TD>
													<INPUT class="monospace" id="j_username" name="j_username" required="" type="text"/>
												</TD>
											</TR>
											<TR>
												<TH class="must">
													<LABEL for="j_password">密码</LABEL>
												</TH>
												<TD>
													<INPUT class="monospace" id="j_password" name="j_password" required="" type="password"/>
												</TD>
											</TR>
											<TR>
												<TD class="textAlignRite" colspan="2">
													<A style="color:#C82506;cursor:help" href="/forgot.asp">忘记密码？重设！</A>
												</TD>
											</TR>
											<TR>
												<TD class="taLeft" colspan="2">
													<INPUT class="fL" style="background-color:#000" type="reset" value="重新填写"/>
													<INPUT class="fR" style="background-color:#F39019" type="submit" value="确认送出"/>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<BR/>
								</FORM>
							</DIV>
							<xsl:call-template name="footer"/>
						</NAV>
					</DIV>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

</xsl:stylesheet>
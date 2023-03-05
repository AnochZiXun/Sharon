/**
 * 結帳付款
 *
 * @author:	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	$('TABLE#store INPUT[type="button"]').click(function () {
		var f = this.form;
		$.post($(f).attr('action'), $(f).serialize(), function (d) {
			if (d.reason) {
				alert(d.reason);
			}
			if (d.AccessToken && d.rtnCode === 0) {
				$('<FORM>', {
					//"action": 'http://demo.cctech-support.com/icarry-as/api/B2CPostRedirect',
					"action": 'http://icarry.me/icarry-as/api/B2CPostRedirect',
					"method": "POST",
					'html': '<INPUT name="AccessToken" type="hidden" value="' + d.AccessToken + '">'
				}).appendTo($('BODY')[0]).submit();
			}
		}, 'json');
	});
});
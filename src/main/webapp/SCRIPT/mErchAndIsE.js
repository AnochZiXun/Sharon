/**
 * (新增|修改)商品
 *
 * @author:	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	$('INPUT[name="withItemSId"]').change(function () {
		var v = $(this).val();
		if (v === 'false') {
			$('TH.withItemSId').removeClass('must');
			$('TH.withoutItemSId').addClass('must');
			$('INPUT.withoutItemSId').attr({disabled: false});
			$('INPUT.withItemSId').attr({disabled: true});
		}
		if (v === 'true') {
			$('TH.withItemSId').addClass('must');
			$('TH.withoutItemSId').removeClass('must');
			$('INPUT.withoutItemSId').attr({disabled: true});
			$('INPUT.withItemSId').attr({disabled: false});
		}
	});
});
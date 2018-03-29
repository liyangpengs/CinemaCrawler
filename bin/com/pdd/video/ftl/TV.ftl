<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>gay里gay气-免费影院-去广告解析-${videoName}</title>
<link rel="icon" href="https://pdd-java.top/static/imgs/favicon.ico">
<link rel="stylesheet" href="/static/css/details.css" type="text/css">
<script type='text/javascript' src='/static/js/jq.js'></script>
<script type='text/javascript' src='/static/js/DPlayer.min.js'></script>
<script type='text/javascript' src='/static/js/hls.js'></script>
<link rel="stylesheet" href="/static/js/DPlayer.min.css">
<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.min.js"></script>
<meta name="referrer" content="never">
<meta name="keywords" content="${keywords}">
<meta name="description" content="${metadescription}">
</head>
<script type="text/javascript">
var VideoInfo=${VideoJson};
</script>
<body>
<header class="header">
	<div class="top-block">
		<div class="logo">
			<a href="/"><img src="https://pdd-java.top/static/imgs/logo.jpg" width="80" height="80" style="vertical-align: middle;">
			<span style="color: green;">gay里gay气</span></a>
		</div>
		<div class="type">
				<div class="link-list">
					<a href="/list?mainType=电视剧">电视剧</a>
					<a href="/list?mainType=电影">电影</a>
					<a href="/list?mainType=综艺">综艺</a>
					<a href="/list?mainType=动漫">动漫</a>
					<a href="/list?mainType=搞笑">搞笑</a>
					<a href="/list?mainType=音乐">音乐</a>
					<a href="/list?mainType=游戏">游戏</a>
					<a href="https://pdd-java.top">pdd养成计划博客</a>
				</div>
		</div>
		<div class="weather">
			<iframe allowtransparency="true" allowfullscreen="true"  frameborder="0" scrolling="no" 
				src="//tianqi.2345.com/plugin/widget/index.htm?s=2&z=3&t=0&v=0&d=2&bd=0&k=000000&f=&ltf=009944&htf=cc0000&q=1&e=1&a=1&c=54511&w=290&h=96&align=right">
			</iframe>
		</div>		
	</div>
</header>
<section class="main">
	<section class="Details-left">
		<div class="details-title"><h1 style="font-size: 18px">${videoName}</h1></div>
		<div id="player" class="Player-div"></div>
		<div class="Playlist">
			<#if mainTypeStr!="电影" && mainTypeStr!="游戏" && mainTypeStr!="音乐">
				<div style="color: white;text-align: center;">选集</div>
				<ul>
				<#list PlayerList as list>
					<li>
						<a href="javascript:void(0)" source-url="${list.sourceUrl}">
							<img alt="" src="${list.cover}">
							<p class="collectionNumber">${list.title}</p>
						</a>
					</li>
				</#list>
				</ul>
			</#if>
		</div>
		<#if mainTypeStr !="游戏" && mainTypeStr !="音乐">
			<#if mainTypeStr=="动漫" || mainTypeStr=="综艺"|| mainTypeStr=="搞笑">
				<div class="desc"><strong>看点:</strong>${watch}</div>
				<div class="desc"><strong>简介:</strong>${description}</div>
			<#else>
				<div class="desc"><strong>导演:</strong>${director}</div>
				<div class="desc"><strong>看点:</strong>${watch}</div>
				<div class="desc"><strong>主演:</strong>${tostarInfo}</div>
				<div class="desc"><strong>简介:</strong>${description}</div>
			</#if>
		</#if>
		<div class="share">
			 <span>分享到:</span>
        	  <div class="bdsharebuttonbox" style="display: inline;">
				   <a href="#" class="bds_more" data-cmd="more" style="float: none;"></a>
				   <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间" style="float: none;"></a>
				   <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博" style="float: none;"></a>
				   <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博" style="float: none;"></a>
				   <a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网" style="float: none;"></a>
				   <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信" style="float: none;"></a>
  				</div>
  			<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":VideoInfo.videoName,"bdDesc":VideoInfo.metadescription,"bdMini":"2","bdMiniList":false,"bdPic":VideoInfo.cover,"bdStyle":"0","bdSize":"24"},"share":{},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
		</div>
	</section>
	<section class="Details-rigth">
		<div class="Advertisement">
			<div class="Advertisement-title">广告位1</div>
			<div class="Advertisement-content">
				<img alt="" src="/static/img/Advertisement.jpg">
			</div>
		</div>
		<div class="Advertisement">
			<div class="Advertisement-title">广告位2</div>
			<div class="Advertisement-content">
				<img alt="" src="/static/img/Advertisement.jpg">
			</div>
		</div>
	</section>
	<div class="Split"></div>
		<div class="Recommend">
		热门电影推荐<span class="chak">
		<a href="/movie">查看更多>></a></span>
	</div>
	<div class="hot">
		<ul class="movie-list">
			<#list hotMovie as movie>
				<li class="item">
					<a class="js-tongjic" href="${movie.playerUrl}" title="${movie.videoName}" target="_blank">
				         <div class="cover g-playicon">
				          <img src="${movie.cover}" alt="${movie.videoName}">
				          <span class="pay">推荐</span>       <span class="hint">${movie.duration}</span>
				         </div>
				         <div class="detail">
				          <p class="title g-clear">
						    <span class="s1">${movie.videoName}</span>
							<span class="s2">${movie.score}</span></p>
				           <p class="star">主演：${movie.tostarInfo}</p>
				          </div>
			         </a>
           		</li>
			</#list>
		</ul>
	</div>
	<div class="Recommend">
		热门电视剧推荐<span class="chak">
		<a href="/more">查看更多>></a></span>
	</div>
	<div class="hot">
		<ul class="movie-list">
			<#list hotTV as movie>
					 <li class="item">
						<a class="js-tongjic" href="${movie.playerUrl}" title="${movie.videoName}" target="_blank">
					         <div class="cover g-playicon">
					          <img src="${movie.cover}" alt="${movie.videoName}">
					          <span class="pay">推荐</span>       <span class="hint">${movie.lastupdateinfo}</span>
					         </div>
					         <div class="detail">
					          <p class="title g-clear">
							    <span class="s1">${movie.videoName}</span>
								<span class="s2"></span></p>
					           <p class="star">主演：${movie.tostarInfo}</p>
					          </div>
				         </a>
	           		</li>	
				</#list>
		</ul>
	</div>
	<div class="copyright">
		<p>本站所有资源只提供web页面，非录制,储存,上传，均为采集资源!如果有侵犯他方权益，请与我方告知，我方将立即删除!</p>
		<p>©2018 gay里gay气-免费影院-去广告解析 技术博客:<a href="http://pdd-java.top">pdd养成计划</a></p>
		<p>QQ：871080854 &nbsp;&nbsp;邮箱:<a href="mailto:871080854@qq.com">871080854@qq.com</a></p>
		<p><a href="http://www.miitbeian.gov.cn">湘ICP备17020198号-2</a></p>
	</div>
</section>
<div class="goTop" style="display: none;">
		<a href="javascript:void(0)"></a>
</div>
</body>
<script type='text/javascript' src='/static/js/jq.js'></script>
<script type="text/javascript">
	$(function(){
		//滚动条事件
		$(window).scroll(function(){	
		    if ($(window).scrollTop() > 100){
				 $(".goTop").fadeIn();
			 }else{
				 $(".goTop").fadeOut();
			 }
		})
		 $(".goTop").click(function(){
			 $('html,body').animate({
			        'scrollTop': 0
			  }, 500);
		})
	})
</script>
<script type="text/javascript" src="/static/js/parse.js"></script>
</html>
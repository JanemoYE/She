<script>
	export default {
		globalData:{//全局变量
			userKey:"",//用户的独立key
			userArrays:{},//用户的个人信息集合
			articleTypeList:["emo","阳光","动漫","朋友圈文案","英文","影视","作文","外语","爱情"],//文章类型，后期添加也在这里，让全局都能调用添加
			BaseUrl:"http://127.0.0.1:8081",
			version:"1.0.9"//当前版本号
		},
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	.lv {
		display: flex;
		color: #fff;
		padding: 5rpx 10rpx;
		border-radius: 50rpx;
		font-size: 26rpx;
		margin-left: 20rpx;
		width: 100rpx;
		font-weight: bold;
		height: 36rpx;
	}
	
	.lv image {
		width: 30rpx;
		margin-top: 1rpx;
	}
	.Exe1{
		background-color: #696969;
	}
	.Exe2{
		background-color: #8A2BE2;
	}
	.Exe3{
		background-color: #CD3333;
	}
	.Exe4{
		background-color: 	#CDB7B5;
	}
	.Exe5{
		background-color: 	#2F4F4F;
	}
	.Exe6{
		background-color: #000;
	}
	
	.xgj-likeButtom{
		border: rgba(0,0,0,0.18) 1px solid;
		width: 200rpx;
		height: 50rpx;
		text-align: center;
		line-height: 50rpx;
		border-radius: 50rpx;
		color: gray;
		font-size: 24rpx;
	}
	.xgj-noLikeButtom{
		border:  #f28500 1px solid;
		width: 200rpx;
		height: 50rpx;
		text-align: center;
		line-height: 50rpx;
		border-radius: 50rpx;
		color: #f28500;
		font-size: 24rpx;
	}
</style>

<template>
	<view>
		<view class="toolTipBg" @click="delToolTip()" v-if="index==1">
			<view class="toolTip">
				<view class="Title">
					检测到新版本
				</view>
				<view class="contentFont">
					是否前往下载？
				</view>
				<view class="btn" @click="copyUrl()">
					复制下载链接
				</view>
			</view>
		</view>
		<view class="me-about" @click="updata()">
			<text>检查更新</text>
			<view style="font-weight: bold;display: flex;">
				<image src="../../../static/next.png" mode="widthFix"></image>
			</view>
		</view>
		<view class="me-about">
			<text>联系客服</text>
			<view style="font-weight: bold;display: flex;">
				<image src="../../../static/next.png" mode="widthFix"></image>
			</view>
		</view>
		<view class="me-about" @click="newLogNav()">
			<text>更新日志</text>
			<view style="font-weight: bold;display: flex;">
				<image src="../../../static/next.png" mode="widthFix"></image>
			</view>
		</view>
		<view class="me-about" @click="delUser()">
			<text>注销账号</text>
			<view style="font-weight: bold;display: flex;">
				<image src="../../../static/next.png" mode="widthFix"></image>
			</view>
		</view>
		
		<view class="me-about">
			<text>开启指纹登录</text>
			<view style="font-weight: bold;display: flex;" @click="offOn()"> 
				<view class="roundBg" :style="test==0 ? 'background-color: #d3d3d3;':'background-color: #29ea1b;'">
					<view class="round" :style="test==0 ? 'margin-left: 0rpx;':'margin-left: 68rpx;'">
						
					</view>
				</view>
			</view>
		</view>

		<view class="me-about1" style="" @click="logout()">
			<text>退出登录</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				version:"",
				newVersion:"",
				url:"http://124.220.174.134:8888/down/2FUDXH9TxNAr",
				index:0,
				test:0
			}
		},
		onLoad() {
			var that=this;
			this.version=getApp().globalData.version;
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Version/VersionInformation",
				success: function(res) {
					// console.log(res.data.data)
					that.newVersion=res.data.data
				}
			})
			// if (getApp().globalData.userKey == "") {
			// 	uni.reLaunch({
			// 		url: "../../login/login"
			// 	})
			// }
		},
		methods: {
			delToolTip(){
				if(this.index==1){
					this.index=0;
				}
			},
			delUser(){
				uni.showToast({
					title: '暂不支持注销！',
					icon:'none',
					duration: 2000
				});
			},
			newLogNav(){
				uni.navigateTo({
					url:'./log/log'
				})
			},
			copyUrl(){
				uni.setClipboardData({ data: this.url });
			},
			updata(){
				if(this.version==this.newVersion){
					uni.showToast({
						title: '已是最新版本',
						icon:'none',
						duration: 2000
					});
				}else{
					// Plus.runtime.openURL("http://aboutlive.top/AboutLive.apk");
					// plus.runtime.openURL("http://baidu.com")
					this.index=1;
				}
			},
			logout() {
				getApp().globalData.userArrays = {}
				getApp().globalData.userKey = ""
				uni.clearStorage()
				uni.showModal({
					title: '提示',
					content: '已退出！',
					success: function(res) {
						if (res.confirm) {
							uni.reLaunch({
								url: "../../login/login"
							})
						} else if (res.cancel) {
							uni.reLaunch({
								url: "../../login/login"
							})
						}
					}
				});
			},
			offOn(){
				if(this.test==0){
					this.test=1
				}else{
					this.test=0
				}
			}
		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}
	.toolTipBg{
		z-index: 3;
		position: fixed;
		top: 0;
		height: 1500rpx;
		width: 100%;
	}
	.me-about {
		background-color: #fff;
		display: flex;
		/* 		margin: 20rpx 40rpx; */
		width: 90%;
		margin: 0 auto;
		padding: 30rpx 10rpx;
		justify-content: space-between;

		/* 		border-bottom: 2rpx solid rgb(238, 238, 238); */
	}

	.me-about image {
		align-self: center;
		width: 35rpx;
		height: 35rpx;
	}

	.me-about1 {
		background-color: #fff;
		margin-top: 20rpx;
		text-align: center;
		font-weight: bold;
		/* 		margin: 20rpx 40rpx; */
		width: 100%;
		background-color: rgba(0, 0, 0, 0.061);
		color: gray;
		padding: 30rpx 10rpx;
		justify-content: space-between;
		/* 		border-bottom: 2rpx solid rgb(238, 238, 238); */
	}
	.toolTip{
		padding: 20rpx;
		position: fixed;
		background-color: #212121;
		width: 400rpx;
		height: 300rpx;
		border-radius: 35rpx;
		border: gray solid 2px;
		top: 300rpx;
		left: 150rpx;
	}
	.toolTip .Title{
		z-index: 2;
		text-align: center;
		font-size: 32rpx;
		margin-top: 10rpx;
		color: #fff;
	}
	.contentFont{
		color: #fff;
		text-align: center;
		margin-top: 20rpx;
		font-size: 24rpx;
	}
	.btn{
		background-color: #fff;
		width: 200rpx;
		padding: 30rpx;
		margin: 0 auto;
		text-align: center;
		margin-top: 50rpx;
		border-radius: 20rpx;
	}
	.roundBg{
		width: 120rpx;
		height: 50rpx;
		border-radius: 35rpx;
		background-color: #29ea1b;
		padding: 5rpx;
		transition: 0.6s;
	}
	.roundBg .round{
		background-color: #fff;
		width: 50rpx;
		height: 50rpx;
		border-radius: 50%;
		transition: 0.6s;
		margin-left: 68rpx;
	}
</style>

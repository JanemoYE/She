<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				
			</view>
			<view :class="type==1? 'btn':'btn1'" @click="typeButton(1)">文章</view>
			<view :class="type==2? 'btn':'btn1'" @click="typeButton(2)">用户</view>
		</view>
		
		<view style="font-size: 28rpx;margin-left: 40rpx;margin-top: 30rpx;color: #212121;">
			搜索的关键词：{{text}}
		</view>
		
		<view v-if="type==1">
			<view v-for="(item,index) in articleList" :key="index">
				<view class="bg" @click="comment(item.id)">
					<view class="image">
						<image :src="item.imagesBase64" mode="widthFix"></image>
					</view>
					<view class="font">
						{{item.content}}
					</view>
					<view style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
						{{item.author}}
					</view>
				</view>
			</view>
		</view>
		
		<view v-if="type==2">
			<view class="list">
				<view v-for="(listFan,index) in userList" :key="index" style="margin-top: 40rpx;">
					<view style="display: flex;">
						<view class="headImage" @click="userHome(listFan[4])">
							<image :src="listFan[0]" mode="widthFix"></image>
						</view>
						<view>
							<view class="userName">
								{{listFan[1]}}
							</view>
							<view class="lv" :class="'Exe'+listFan[2]">
								<image src="../../../static/lv.png" mode="widthFix"></image>
								<view>
									Lv-{{listFan[2]}}
								</view>
							</view>
						</view>
						<view class="xgj-noLikeButtom" style="margin-top: 25rpx;" @click="userHome(listFan[4])"  v-if="listFan[3]=='false'">
							+ 关注
						</view>
						<view class="xgj-likeButtom" style="margin-top: 25rpx;" v-if="listFan[3]=='true'">
							已关注
						</view>
					</view>
					<view class="hr"></view>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				text: "空",
				type:1,
				articleList:{},
				userList:{}
			}
		},
		onLoad(text) {
			uni.showLoading({
				title: '加载中...'
			});
			this.text = text.text
			// console.log(text.text)
			var that = this;
			uni.request({
				data: {
					text: text.text
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/searchContent",
				success: function(res) {
					if (res.data.code == 0) {
						that.articleList = res.data.data
						// console.log(res.data.data)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}

				}
			})
			
			uni.request({
				data: {
					text: text.text,
					userKey:getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/User/searchContent",
				success: function(res) {
					if (res.data.code == 0) {
						that.userList = res.data.data
						console.log(res.data.data)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			
			uni.hideLoading();
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			typeButton(id){
				this.type=id
			},
			userHome(userKey) {
				uni.navigateTo({
					url: "../../userHome/userHome?userKey=" +userKey
				});
			},
			comment(id) {
				uni.navigateTo({
					url: "../../comment/comment?id=" + id
				});
			}
		}
	}
</script>

<style>
	page {
		background-color: #f4f4f4;
		font-size: 26rpx;
	}
	.title{
		width: 500rpx;
		text-align: center;
		font-size: 32rpx;
	}
	.titleTop {
		padding-top: 60rpx;
		width: 90%;
		margin-top: 20rpx;
		display: flex;
		justify-content: space-between;
		margin-left: 40rpx;
	}

	.titleTop .back image {
		width: 40rpx;
	}
	
	.btn{
		
		margin-left: 20rpx;
		width: 100rpx;
		padding: 15rpx;
		background-color: #000000;
		color: #fff;
		border-radius: 20rpx;
		text-align: center;
		transition: 0.6s;
	}
	.btn1{
		margin-left: 20rpx;
		width: 100rpx;
		padding: 15rpx;
		background-color: gray;
		color: #fff;
		border-radius: 20rpx;
		text-align: center;
		transition: 0.6s;
	}
	.bg{
		background-color: #fff;
		width: 90%;
		margin: 0 auto;
		margin-top: 40rpx;
		padding: 50rpx 20rpx;
		border-radius: 20rpx;
	}
	.bg .image{
		border-radius: 20rpx;
		overflow: hidden;
		text-align: center;
	}
	.bg .image image{
		border-radius: 20rpx;
		width: 80%;
	}
	.bg .font{
		color: #212121;
		line-height: 55rpx;
		font-size: 32rpx;
		width: 95%;
		margin: 0 auto;
	}
	.list{
		width: 95%;
		margin: 0 auto;
		margin-top: 40rpx;
	}
	.list .headImage image{
		width: 120rpx;
		border-radius: 50%;
	}
	.list .userName{
		font-size: 34rpx;
		margin-left: 20rpx;
		margin-top: 5rpx;
		margin-bottom: 20rpx;
		width: 400rpx;
		margin-right: 50rpx;
		overflow: hidden;
	}
</style>

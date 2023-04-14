<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				排行
			</view>
			<view class="save"></view>
		</view>
		
		<view class="leftTitle">
			<view :class="item==nowTypeName ? 'typeTrue':'typeFlase'" v-for="(item,index) in leftTitleList" :key="index"
			@click="updataTypeName(item)">
				{{item}}
			</view>
		</view>
		
		<view v-if="nowTypeName=='收藏榜'">
			<view class="list">
				<view v-for="(listFan,index) in articleList" :key="index" style="margin-top: 20rpx;">
					<view>
						<view  style="display: flex;">
							<view class="rank" v-if="index!=0 && index!=1 && index!=2">
								<view style="width: 50rpx;">
									{{index+1}}
								</view>
							</view>
							
							<!-- 第一样式 -->
							<view class="rank1" v-if="index==0">
								<view style="width: 50rpx;">
									{{index+1}}
								</view>
							</view>
							<!-- 第二样式 -->
							<view class="rank2" v-if="index==1">
								<view style="width: 50rpx;">
									{{index+1}}
								</view>
							</view>
							<!-- 第三样式 -->
							<view class="rank3" v-if="index==2">
								<view style="width: 50rpx;">
									{{index+1}}
								</view>
							</view>
							
							<view class="headImage" @click="comment(listFan[0])">
								{{listFan[1]}}
							</view>
						</view>
						<view style="display: flex;">
							<view style="margin-left: 90rpx;color: gray;margin-bottom: 20rpx;margin-top: 20rpx;">
								{{listFan[4]}} 收藏 · {{listFan[3]}}
							</view>
							<view>
								<image :src="listFan[2]" mode="widthFix" style="width: 40rpx;border-radius: 50%;margin-left: 20rpx;margin-top: 15rpx;"></image>
							</view>
						</view>
					</view>
					<view class="hr"></view>
				</view>
			</view>
		</view>
		
		<view v-if="nowTypeName=='作者总榜'">
			<view class="list">
				<view v-for="(listFan,index) in userList" :key="index" style="margin-top: 20rpx;">
					<view style="display: flex;">
						<view class="rank" v-if="index!=0 && index!=1 && index!=2">
							<view style="width: 50rpx;">
								{{index+1}}
							</view>
						</view>
						
						<!-- 第一样式 -->
						<view class="rank1" v-if="index==0">
							<view style="width: 50rpx;">
								{{index+1}}
							</view>
						</view>
						<!-- 第二样式 -->
						<view class="rank2" v-if="index==1">
							<view style="width: 50rpx;">
								{{index+1}}
							</view>
						</view>
						<!-- 第三样式 -->
						<view class="rank3" v-if="index==2">
							<view style="width: 50rpx;">
								{{index+1}}
							</view>
						</view>
						
						<view class="headImage" @click="userHome(listFan[0])">
							<image :src="listFan[2]" mode="widthFix"></image>
						</view>
						<view>
							<view class="userName">
								{{listFan[1]}}
							</view>
							<view style="margin-left: 20rpx;color: gray;margin-bottom: 20rpx;">
								{{listFan[3]}} 综合指标 · {{listFan[4]}} 粉丝数
							</view>
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
				nowTypeName:"作者总榜",
				leftTitleList:["作者总榜","收藏榜"],
				userList:{},//作者总榜list
				articleList:{}//收藏榜list
			}
		},
		onLoad(){
			uni.showLoading({
				title: '加载中...'
			});
			var that=this;
			if(this.userList!={}){
				uni.request({
					data: {},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/User/rankUser100",
					success: function(res) {
						if (res.data.code == 0) {
							that.userList = res.data.data
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
			}
			
			if(this.articleList!={}){
				uni.request({
					data: {},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Article/articleLike100",
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
						uni.hideLoading();
					}
				})
			}
			
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			updataTypeName(s){
				// console.log(s)
				this.nowTypeName=s;
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
	page{
		font-size: 24rpx;
	}
	.titleTop {
		z-index: 1;
		padding-top: 80rpx;
		width: 100%;
		display: flex;
		justify-content: space-between;
		padding-left: 20rpx;
		position: fixed;
		top: 0;
		background-color: #fff;
		border-bottom: #f1f1f1 solid 1px;
		padding-bottom: 20rpx;
	}

	.titleTop .back image {
		width: 40rpx;
		margin-left: 20rpx;
	}

	.titleTop .title {
		/* font-weight: bold; */
	}

	.titleTop .save {
		width: 70rpx;
		margin-top: 6rpx;
	}
	
	.leftTitle{
		background-color: #f1f1f1;
		width: 150rpx;
		height: 1500rpx;
		position: fixed;
		top: 150rpx;
		font-size: 26rpx;
		text-align: center;
	}
	.leftTitle .typeTrue{
		background-color: #fff;
		padding: 25rpx 10rpx;
		border-right: #212121 2px solid;
	}
	.leftTitle .typeFlase{
		padding: 25rpx 10rpx;
	}
	
	.list{
		z-index: 2;
		width: 500rpx;
		margin-left: 150rpx;
		margin-top: 180rpx;
	}
	.list .headImage image{
		width: 70rpx;
		border-radius: 50%;
	}
	.list .userName{
		font-size: 28rpx;
		margin-left: 20rpx;
		margin-top: 5rpx;
		/* margin-bottom: 20rpx; */
		width: 400rpx;
		margin-right: 50rpx;
		overflow: hidden;
	}
	.hr{
		width: 80%;
		margin: 0 auto;
		height: 2rpx;
		background-color: #efefef;
		margin: 10rpx;
		margin-left: 50rpx;
	}
	.rank{
		width: 60rpx;
		font-size: 24rpx;
		/* font-weight: bold; */
		text-align: center;
		color: #fff;
		background-color: #212121;
		margin-left: 20rpx;
		height: 30rpx;
		margin-right: 15rpx;
		border-radius: 10rpx;
		padding: 5rpx;
	}
	.rank1{
		background-color: #FFD700;
		width: 60rpx;
		font-size: 24rpx;
		/* font-weight: bold; */
		text-align: center;
		color: #fff;
		margin-left: 20rpx;
		height: 30rpx;
		margin-right: 15rpx;
		border-radius: 10rpx;
		padding: 5rpx;
	}
	.rank2{
		background-color: #C0C0C0;
		width: 60rpx;
		font-size: 24rpx;
		/* font-weight: bold; */
		text-align: center;
		color: #fff;
		margin-left: 20rpx;
		height: 30rpx;
		margin-right: 15rpx;
		border-radius: 10rpx;
		padding: 5rpx;
	}
	.rank3{
		background-color: rgb(184,115,51);
		width: 60rpx;
		font-size: 24rpx;
		/* font-weight: bold; */
		text-align: center;
		color: #fff;
		margin-left: 20rpx;
		height: 30rpx;
		margin-right: 15rpx;
		border-radius: 10rpx;
		padding: 5rpx;
	}
</style>

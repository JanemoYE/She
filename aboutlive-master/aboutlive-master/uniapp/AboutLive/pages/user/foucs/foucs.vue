<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				我的关注
			</view>
			<view style="width: 50rpx;"></view>
		</view>
		
		<view class="list">
			<!-- 循环列表 -->
			<view v-for="(listFoucs,index) in listFoucs" :key="index">
				<view style="display: flex;">
					<view class="headImage" @click="userHome(listFoucs[3])">
						<image :src="listFoucs[0]" mode="widthFix"></image>
					</view>
					<view>
						<view class="userName">
							{{listFoucs[1]}}
						</view>
						<view class="lv" :class="'Exe'+listFoucs[2]">
							<image src="../../../static/lv.png" mode="widthFix"></image>
							<view>
								Lv-{{listFoucs[2]}}
							</view>
						</view>
					</view>
					<view class="xgj-likeButtom" style="margin-top: 25rpx;">
						已关注
					</view>
				</view>
				<view class="hr"></view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listFoucs:[]
			}
		},
		onLoad() {
			var that=this;
			uni.request({
				data: {
					userKey:getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/User/focusList",
				success: function(res) {
					if (res.data.code == 0) {
						that.listFoucs=res.data.data
						// console.log(that.listFoucs)
					} else {
						uni.showToast({
							title: '好像没有查到什么呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			userHome(userKey) {
				uni.navigateTo({
					url: "../../userHome/userHome?userKey=" +userKey
				});
			},
		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}
	.hr {
		width: 80%;
		background-color: rgba(0, 0, 0, 0.06);
		height: 1px;
		margin: 0 auto;
		margin-top: 30rpx;
		margin-bottom: 30rpx;
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

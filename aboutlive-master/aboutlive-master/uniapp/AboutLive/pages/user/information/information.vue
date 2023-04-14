<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				编辑个人信息
			</view>
			<view class="save" @click="login()">保存</view>
		</view>

		<view class="content">
			<view class="type">
				<!--头像-->
				<view class="text" style="margin-top: 40rpx;">头像</view>
				<view class="image">
					<image :src="userArrays[2]" mode="widthFix"></image>
				</view>
			</view>

			<view class="hr"></view>

			<view class="type">
				<view class="text">昵称</view>
				<view class="input">
					<input type="text" style="font-size: 28rpx;" @input="newNameInput($event)" :value="newName" />
				</view>
			</view>

			<view class="hr"></view>

			<view class="type">
				<view class="text">性别</view>
				<view class="sex">
					<view :class="newSex=='1'? 'boy':'noSex'" @click="sexChoose(1)">男</view>
					<view :class="newSex=='2'? 'girl':'noSex'" @click="sexChoose(2)">女</view>
				</view>
			</view>

			<view class="hr"></view>

			<view class="type">
				<view class="text">邮箱</view>
				<view class="input">
					<input type="text" style="font-size: 28rpx;" :value="newEmail" @input="newEmailInput($event)" />
				</view>
			</view>

			<view class="hr"></view>

			<view class="type">
				<view class="text">出生日期</view>
				<view class="">
					<picker mode="date" :value="newBorn" @change="bindDateChange">
						<view>{{ newBorn }}</view>
					</picker>
				</view>
			</view>
			<view class="hr"></view>
		</view>
		
		<view class="hint">
			选填项
		</view>
		
		<view class="content" style="margin-top: 10rpx;">
			<view class="hr"></view>
			<view class="type">
				<view class="text" style="height: 80rpx;width: 450rpx;">QQ号(配对QQ号头像,头像改变即为成功,不显示无需重复绑定)</view>
				<view class="input">
					<input type="text" style="font-size: 28rpx;" placeholder="输入绑定的QQ号" @input="newQqInput($event)" />
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userArrays: {},
				newImages: "",
				newName: "",
				newSex: "",
				newEmail: "",
				newBorn: "",
				qq:"",
				// startDate: getDate('start'),
				// endDate: getDate('end')
			}
		},
		onLoad() {
			this.userArrays = getApp().globalData.userArrays
			this.newName = this.userArrays[1]
			this.newImages = this.userArrays[2]
			this.newSex = this.userArrays[4]
			this.newEmail = this.userArrays[6]
			this.newBorn = this.userArrays[7]
		},
		methods: {
			// getDate(type) {
			// 	const date = new Date();
			
			// 	let year = date.getFullYear();
			// 	let month = date.getMonth() + 1;
			// 	let day = date.getDate();
			
			// 	if (type === 'start') {
			// 		year = year - 10;
			// 	} else if (type === 'end') {
			// 		year = year + 10;
			// 	}
			// 	month = month > 9 ? month : '0' + month;
			// 	day = day > 9 ? day : '0' + day;
			
			// 	return `${year}-${month}-${day}`;
			// },
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			newNameInput(value) {
				this.newName = value.detail.value;
			},
			newEmailInput(value) {
				this.newEmail = value.detail.value;
			},
			sexChoose(e) {
				this.newSex = e;
			},
			bindDateChange: function(e) {
				this.newBorn = e.detail.value;
			},
			newQqInput(e){
				this.qq=e.detail.value;
			},
			login() {
				var that = this;
				uni.showLoading({
					title: '加载中...'
				});
				if(this.qq!="" && this.qq!=null){
					this.newImages="https://q1.qlogo.cn/g?b=qq&nk="+this.qq+"&s=640"
				}
				if (this.newSex != "" && this.newName != "" && this.newImages != "" && this.newEmail != "") {
					uni.request({
						data: {
							headImage: that.newImages,
							name: that.newName,
							sex: that.newSex,
							email: that.newEmail,
							born: that.newBorn,
							userKey: getApp().globalData.userKey
						},
						header: { //PUT必须添加
							'content-type': "application/x-www-form-urlencoded",
						},
						method: "PUT",
						url: getApp().globalData.BaseUrl+"/User/update",
						success: function(res) {
							if (res.data.code == 0) {
								getApp().globalData.userArrays[1] = that.newName
								getApp().globalData.userArrays[2] = that.newImages
								getApp().globalData.userArrays[4] = that.newSex
								getApp().globalData.userArrays[6] = that.newEmail
								getApp().globalData.userArrays[7] = that.newBorn
								uni.showToast({
									title: '修改成功！资料更新需要一段时间...',
									icon: 'none',
									duration: 2000
								});
							} else {
								uni.showToast({
									title: '修改失败！',
									icon: 'none',
									duration: 2000
								});
							}

						}
					})

				} else {
					uni.showToast({
						title: '您填写的内容有空，请勿留空哦！',
						icon: 'none',
						duration: 2000
					});
				}
				uni.hideLoading();
			}

		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}
	.hr {
		width: 100%;
		background-color: rgba(0, 0, 0, 0.1);
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
		position: fixed;
		top: 0;
		left: 40rpx;
	}

	.titleTop .back image {
		width: 40rpx;
	}

	.titleTop .title {
		font-weight: bold;
	}

	.titleTop .save {
		font-size: 24rpx;
		margin-top: 6rpx;
		color: #3F51B5;
	}

	.content {
		width: 90%;
		margin: 0 auto;
		margin-top: 150rpx;
	}

	.content .type {
		display: flex;
		justify-content: space-between;

	}

	.content .type .text {
		color: gray;
		font-size: 28rpx;
	}

	.content .type .image image {
		width: 100rpx;
		border-radius: 50%;
	}

	.content .type .input {
		text-align: right;
	}

	.content .type .sex {
		display: flex;
		font-weight: bold;
	}

	.content .sex .boy {
		color: #1296db;
		background-color: rgba(51, 153, 255, 0.2);
		padding: 05rpx 20rpx;
		border-radius: 10rpx;
		margin-left: 20rpx;
	}

	.content .sex .girl {
		color: #d4237a;
		background-color: rgba(255, 51, 153, 0.2);
		padding: 05rpx 20rpx;
		border-radius: 10rpx;
		margin-left: 20rpx;
	}

	.noSex {
		color: gray;
		background-color: rgba(0, 0,0, 0.04);
		padding: 05rpx 20rpx;
		border-radius: 10rpx;
		margin-left: 20rpx;
	}
	.hint{
		color: #fff;
		background-color: #212121;
		width: 150rpx;
		text-align: center;
		padding: 10rpx;
		margin: 40rpx;
		border-radius: 25rpx;
	}
</style>

<template>
	<view>
		<view class="title" style="color: #fff;">
			Register In
		</view>

		<view class="userCall">
			<view class="fontTitle" style="width: 80rpx;">用户名</view>
			<view class="input">
				<input type="text" placeholder="输入用户名" @input="nameInput($event)" />
			</view>
		</view>

		<!-- 输入注册QQ号 -->
		<view class="userCall">
			<view class="fontTitle" style="width: 150rpx;">UserName</view>
			<view class="input">
				<input type="text" :value="UserName" placeholder="输入账号" @input="userNameInput($event)" />
			</view>
		</view>

		<view v-if="UserName==''"
			style="color: red;font-size: 20rpx;width: 95%;margin: 0 auto; text-align: right;margin-top: 10rpx;">
			x账号输入不规范！无法成功注册！
		</view>



		<!-- 输入注册密码 -->
		<view class="userCall" id="userPwdBorder">
			<view class="fontTitle">UserPwd</view>
			<view class="input">
				<input type="password" placeholder="输入六位以上的密码" @input="PassWordInput($event)" />
			</view>
		</view>

		<!-- 确认密码 -->
		<view class="userCall" id="userPwdBorder">
			<view class="fontTitle">PwdTrue</view>
			<view class="input">
				<input type="password" placeholder="确认密码" @input="PassWordInputTrue($event)" />
			</view>
		</view>
		<view v-if="PassWord!=PassWordTrue"
			style="color: red;font-size: 20rpx;width: 95%;margin: 0 auto; text-align: right;margin-top: 10rpx;">
			x 两次输入的密码不一致！
		</view>

		<!--Sign In提交按钮-->
		<view class="button" @click="sumbit()">
			Register(注册)
		</view>

		<view class="button" @click="back()">
			返回登入
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				UserName: "", //用户账号
				PassWord: "",
				PassWordTrue: "",
				UnameTrue: false,
				name: "" //用户名
			}
		},
		methods: {
			// http: //q1.qlogo.cn/g?b=qq&nk=945794520&s=100
			back() {
				uni.navigateBack({
					delta: 1
				})
			},

			nameInput(value) {
				this.name = value.detail.value
			},

			userNameInput(value) {
				var text = value.detail.value;
				text = text.charAt(text.length - 1);
				// console.log(text)
				this.UserName = value.detail.value
				if (text <= 'z' && text >= 'a' || text <= 'Z' && text >= 'A' ||
					text <= '9' && text >= '0') {
					// console.log("true")
				} else if (text == '' || text == ' ') {
					this.UserName = ""
					// console.log(this.UserName)
				} else {
					this.UserName = ""
					// console.log(this.UserName)
				}
			},

			PassWordInput(value) {
				this.PassWord = value.detail.value
			},

			PassWordInputTrue(value) {
				this.PassWordTrue = value.detail.value
			},

			sumbit() {
				uni.showLoading({
					title: '加载中...'
				});
				var that = this;
				uni.request({
					data: {
						UName: this.UserName
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/User/detect",
					success: function(res) {
						console.log(res.data)
						if (res.data.code == 1) {
							that.UnameTrue = false
							uni.showToast({
								title: '账号已被注册！',
								icon: 'none',
								duration: 2000
							});
						} else {
							that.UnameTrue = true
							if (true) {
								if (that.UserName != "" && that.PassWord != "" && that.PassWord == that.PassWordTrue) {
									uni.request({
										data: {
											name: that.name,
											UName: that.UserName,
											UPwd: that.PassWord
										},
										header: {
											'Content-Type': 'application/x-www-form-urlencoded'
										},
										method: "POST",
										url: getApp().globalData.BaseUrl + "/User/register",
										success: function(res) {
											if (res.data.code == 0) {
												uni.showToast({
													title: '注册成功！',
													duration: 2000
												});
												uni.hideLoading();
												uni.navigateTo({
													url: '../login/login'
												})
											} else {
												uni.showToast({
													title: '注册失败！',
													icon: 'error',
													duration: 2000
												});
											}
							
										}
									})
							
								} else if (that.PassWord != that.PassWordTrue) {
									uni.showModal({
										content: "两次输入的密码不相同！"
									})
								} else {
									uni.showModal({
										content: "请勿留空！"
									})
								}
							}
						}
					}
				})
				
				
			}
		}
	}
</script>

<style>
	page {
		background-color: #212121;
		font-size: 26rpx;
	}

	.title {
		font-size: 8vw;
		text-align: center;
		padding-top: 18vw;
	}

	/* 表单内容样式开始 */

	.userNameHome {
		margin-top: 8vw;
		display: flex;
		justify-content: space-evenly;
	}

	.userNameHome .userName {
		border: 2px solid #fff;
		width: 40vw;
		border-radius: 5vw;
		padding: 1.3vw;
	}

	.userNameHome .year {
		border: 2px solid #fff;
		width: 40vw;
		border-radius: 5vw;
		padding: 1.3vw;
	}

	.userNameHome .fontTitle {
		color: #fff;
		font-size: 3vw;
		background-color: #212121;
		margin-top: -3vw;
		width: 13vw;
		text-align: center;
		margin-left: 4vw;
	}

	.userNameHome .userName .input input {
		width: 85%;
		margin-left: 2vw;
		margin-top: -0.5vw;
		border: none;
		outline: none;
		background-color: #212121;
		font-size: 3vw;
	}

	.userNameHome .year .input input {
		width: 85%;
		margin-left: 2vw;
		margin-top: -0.5vw;
		border: none;
		outline: none;
		background-color: #212121;
		font-size: 3vw;
	}

	.userNameHome .year {
		border: 2px solid #fff;
		width: 40vw;
		border-radius: 5vw;
	}

	/*按钮样式*/
	.button {
		text-align: center;
		background-color: #3F51B5;
		padding: 2vw 6vw;
		width: 70vw;
		height: 10vw;
		margin: 0 auto;
		border-radius: 3vw;
		font-size: 3vw;
		color: #fff;
		margin-top: 50rpx;
		line-height: 70rpx;
	}

	/* 手机号 */
	.userCall {
		border: 2px solid #fff;
		width: 90%;
		border-radius: 5vw;
		padding: 1.3vw;
		margin: 0 auto;
		margin-top: 8vw;
	}

	.userCall .fontTitle {
		color: #fff;
		font-size: 3vw;
		background-color: #212121;
		margin-top: -4vw;
		width: 20vw;
		text-align: center;
		margin-left: 4vw;
	}

	.userCall .input input {
		width: 85%;
		margin-left: 2vw;
		margin-top: -0.5vw;
		border: none;
		outline: none;
		background-color: #212121;
		font-size: 3vw;
		height: 6vw;
	}

	/* 手机号错误提示 */
	.errorHint {
		display: none;
		color: red;
		font-size: 3vw;
		text-align: right;
		margin-right: 12vw;
		transition: 0.2s;
		margin-top: 2vw;
	}

	/* 邮箱验证 */
	.EmailCode {
		background-color: #3F51B5;
		width: 25vw;
		height: 8vw;
		line-height: 8vw;
		text-align: center;
		font-size: 3vw;
		border-radius: 2vw;
		margin-top: 2vw;
		margin-left: 70%;
	}

	/* 性别样式 */
	.sex {
		border: 2px solid #fff;
		width: 20%;
		border-radius: 5vw;
		padding: 1.3vw;
		margin-top: 8vw;
		margin-left: 3vw;
	}

	.sex select {
		color: #fff;
		width: 90%;
		border: none;
		outline: none;
		background-color: #212121;
		margin-top: 0.4vw;
		text-align: center;
		font-size: 3vw;
	}

	/* 全局css 用来进行昼夜css改变 */
	input {
		color: #fff;
	}

	.errorYear {
		display: none;
		color: red;
		font-size: 3vw;
		text-align: right;
		margin-right: 12vw;
		transition: 0.2s;
	}
</style>

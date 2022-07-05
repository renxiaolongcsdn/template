<template>
  <div style="width: 100%; height: 100vh; overflow: hidden">
    <div style="width: 400px ;margin: 150px auto">
      <!--登录 和注册卡片切换  -->
      <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="登录" name="first">      <!--登录卡片-->

          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
              <el-input prefix-icon="el-icon-s-custom" v-model="ruleForm.username"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input prefix-icon="el-icon-lock" type="password" v-model="ruleForm.password" autocomplete="off"
                        show-password></el-input>
            </el-form-item>

            <div style="padding-bottom: 20px">
              <SliderCheck ref="slidercheck" style="width: 100% ;"/>
            </div>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>

        </el-tab-pane>

        <el-tab-pane label="注册" name="second">   <!--注册卡片-->

          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
              <el-input prefix-icon="el-icon-s-custom" v-model="ruleForm.username"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input prefix-icon="el-icon-lock" type="password" v-model="ruleForm.password" autocomplete="off"
                        show-password></el-input>
            </el-form-item>

            <el-form-item label="昵称" prop="nickName">
              <el-input prefix-icon="el-icon-s-custom" type="text" v-model="ruleForm.nickName"
                        autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>

        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import SliderCheck from "@/components/SliderCheck";

export default {
  name: "login",
  components: {
    SliderCheck
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      activeName: 'first',
      ruleForm: {
        username: 'user',
        password: 'user',
        nickName: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 5, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    /*卡片切换*/
    handleClick(tab, event) {
      console.log(this.activeName == "first" ? "first" : "second")
    },
    /*登录或则注册*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

          if (this.activeName == "first") {
            //验证登录滑块
            if (!this.$refs.slidercheck.getconfirmSuccessState()) {
              this.$message({
                message: '请拖动滑块验证！',
                type: 'warning'
              });
              return;
            }
          }
          request.post(this.activeName == "first" ? "/user/login" : "/user/register", this.ruleForm).then(response => {
            if (null != response.data) {
              if (this.activeName == "first") {
                if (response.data.username != 'rxl') {   //普通用户进入博客前台首页
                  this.$router.push({path: '/'});
                  const {href} = this.$router.resolve({
                    path: '/', // 这里写的是要跳转的路由地址
                    query: {}// 页面参数
                  })
                  window.open(href, '_self')
                }
                //登录成功后组要存取用户信息到sessionstorage 中
                console.log("存入用户信息" + JSON.stringify(response.data))
                sessionStorage.setItem("userInfo", JSON.stringify(response.data));
              } else {
                this.$message({
                  message: '注册成功！',
                  type: 'success'
                });
                this.activeName = "first";
              }
            } else {
              if (this.activeName == "first") {
                this.$message({
                  message: '登录失败！',
                  type: 'warning'
                });
              } else {
                this.$message({
                  message: '注册失败！',
                  type: 'warning'
                });
              }
            }
          }).catch(res => {
            if (this.activeName == "first") {
              this.$message({
                message: '登录失败！',
                type: 'error'
              });
            } else {
              this.$message({
                message: '注册失败！',
                type: 'error'
              });
            }

          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  }
}
</script>

<style scoped>

/* From www.lingdaima.com */
.btn {
  height: 2.8em;
  width: 9em;
  background: transparent;
  -webkit-animation: jello-horizontal 0.9s both;
  animation: jello-horizontal 0.9s both;
  border: 2px solid #016DD9;
  outline: none;
  color: #016DD9;
  font-size: 17px;
}

.btn:hover {
  background: #016DD9;
  color: #ffffff;
  animation: squeeze3124 0.9s both;
}

@keyframes squeeze3124 {
  0% {
    -webkit-transform: scale3d(1, 1, 1);
    transform: scale3d(1, 1, 1);
  }

  30% {
    -webkit-transform: scale3d(1.25, 0.75, 1);
    transform: scale3d(1.25, 0.75, 1);
  }

  40% {
    -webkit-transform: scale3d(0.75, 1.25, 1);
    transform: scale3d(0.75, 1.25, 1);
  }

  50% {
    -webkit-transform: scale3d(1.15, 0.85, 1);
    transform: scale3d(1.15, 0.85, 1);
  }

  65% {
    -webkit-transform: scale3d(0.95, 1.05, 1);
    transform: scale3d(0.95, 1.05, 1);
  }

  75% {
    -webkit-transform: scale3d(1.05, 0.95, 1);
    transform: scale3d(1.05, 0.95, 1);
  }

  100% {
    -webkit-transform: scale3d(1, 1, 1);
    transform: scale3d(1, 1, 1);
  }
}

</style>
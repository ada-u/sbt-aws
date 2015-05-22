package com.chatwork.sbt.aws.eb

import com.chatwork.sbt.aws.core.SbtAwsCoreKeys
import com.chatwork.sbt.aws.eb.SbtAwsEb._
import com.chatwork.sbt.aws.s3.SbtAwsS3Plugin
import sbt.Keys._
import sbt._

object SbtAwsEbPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override def requires: Plugins = SbtAwsS3Plugin

  object autoImport extends SbtAwsEbKeys

  import SbtAwsCoreKeys._
  import SbtAwsEbKeys._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    ebBundleTargetFiles in aws := Seq.empty,
    ebBundleFileName in aws := "bundle.zip",
    ebS3BucketName in aws := "eb-bucket",
    ebS3KeyCreator in aws := identity,
    ebApplicationName in aws := (name in thisProjectRef).value,
    ebApplicationDescription in aws := None,
    ebVersionLabel in aws := (version in thisProjectRef).value,
    ebBuildBundle in aws <<= ebBuildBundleTask(),
    ebUploadBundle in aws <<= ebUploadBundleTask(),
    ebCreateApplication in aws <<= ebCreateApplicationTask,
    ebCreateApplicationVersion in aws <<= ebCreateApplicationVersionTask
  )

}
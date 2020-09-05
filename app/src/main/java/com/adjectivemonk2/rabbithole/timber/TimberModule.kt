package com.adjectivemonk2.rabbithole.timber

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import timber.log.Tree

@Module
@InstallIn(ApplicationComponent::class)
abstract class TimberModule {

  @Binds abstract fun bindTree(tree: LogcatTree): Tree
}

package gay.pyrrha.dailyxp.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gay.pyrrha.dailyxp.core.data.repository.OfflineFirstQuestDataRepository
import gay.pyrrha.dailyxp.core.data.repository.OfflineFirstUserDataRepository
import gay.pyrrha.dailyxp.core.data.repository.QuestDataRepository
import gay.pyrrha.dailyxp.core.data.repository.UserDataRepository
import gay.pyrrha.dailyxp.core.data.util.ConnectivityManagerNetworkMonitor
import gay.pyrrha.dailyxp.core.data.util.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository
    ): UserDataRepository

    @Binds
    internal abstract fun bindsQuestDataRepository(
        questDataRepository: OfflineFirstQuestDataRepository
    ) : QuestDataRepository

//    @Binds
//    internal abstract fun bindsNetworkMonitor(
//        networkMonitor: ConnectivityManagerNetworkMonitor
//    ) : NetworkMonitor
}

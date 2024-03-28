package gay.pyrrha.dailyxp.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gay.pyrrha.dailyxp.core.datastore.QuestsProto
import gay.pyrrha.dailyxp.core.datastore.QuestsSerializer
import gay.pyrrha.dailyxp.core.datastore.UserPreferences
import gay.pyrrha.dailyxp.core.datastore.UserPreferencesSerializer
import gay.pyrrha.dailyxp.core.network.Dispatcher
import gay.pyrrha.dailyxp.core.network.XpDispatchers.Io
import gay.pyrrha.dailyxp.core.network.di.ApplicationScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    internal fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(Io) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = emptyList()
        ) {
            context.dataStoreFile("user_preferences.pb")
        }

    @Provides
    @Singleton
    internal fun providesQuestsDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(Io) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        questsSerializer: QuestsSerializer
    ) : DataStore<QuestsProto> =
        DataStoreFactory.create(
            questsSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = emptyList()
        ) {
            context.dataStoreFile("quests.pb")
        }
}

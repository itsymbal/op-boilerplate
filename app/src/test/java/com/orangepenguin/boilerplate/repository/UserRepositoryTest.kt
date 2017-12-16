package com.orangepenguin.boilerplate.repository

import com.orangepenguin.boilerplate.rest.GitHubClientBuilder
import com.orhanobut.mockwebserverplus.MockWebServerPlus
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {

    @JvmField
    @Rule
    var server = MockWebServerPlus()
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        val serverBaseUrl = server.url("/")

        println("serverUrl '$serverBaseUrl'")
        userRepository = UserRepository(GitHubClientBuilder.getGitHubClient(serverBaseUrl))
    }

    @Test
    @Throws(Exception::class)
    fun fetchUserSync() {
        server.enqueue("github_user")
        val user = userRepository.fetchUserSync("itsymbal")
    }
}

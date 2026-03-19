package com.acedatacloud.mcp.veo

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service(Service.Level.APP)
@State(name = "McpSettings_veo", storages = [Storage("McpVeoSettings.xml")])
class McpSettings : PersistentStateComponent<McpSettings.State> {

    data class State(
        var apiToken: String = "",
        var hasShownSetupNotification: Boolean = false
    )

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    companion object {
        fun getInstance(): McpSettings =
            ApplicationManager.getApplication().getService(McpSettings::class.java)
    }

    fun getStdioConfig(): String {
        val token = myState.apiToken.ifEmpty { "YOUR_API_TOKEN" }
        return """{"mcpServers": {"veo": {"command": "uvx", "args": ["mcp-veo"], "env": {"ACEDATACLOUD_API_TOKEN": "$$token"}}}}"""
    }

    fun getHttpConfig(): String {
        val token = myState.apiToken.ifEmpty { "YOUR_API_TOKEN" }
        return """{"mcpServers": {"veo": {"url": "https://veo.mcp.acedata.cloud/mcp", "headers": {"Authorization": "Bearer $$token"}}}}"""
    }
}

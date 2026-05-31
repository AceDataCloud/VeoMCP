# Veo MCP

Google Veo video generation — Veo 2, Veo 3, Veo 3.1 (incl. fast variants and upscale).

[![VS Code Marketplace](https://img.shields.io/visual-studio-marketplace/v/acedatacloud.mcp-veo?label=VS%20Code)](https://marketplace.visualstudio.com/items?itemName=acedatacloud.mcp-veo) [![PyPI](https://img.shields.io/pypi/v/mcp-veo.svg?label=PyPI)](https://pypi.org/project/mcp-veo/) [![Hosted MCP](https://img.shields.io/badge/hosted-mcp-blue)](https://veo.mcp.acedata.cloud/mcp)

Generate AI video with Google Veo from text or images, including high-quality Veo 3.1 and the fast variants. Supports 1080p upscale.

This extension registers the **veo** MCP server with VS Code so GitHub
Copilot and any other agent that speaks the [Model Context Protocol](https://modelcontextprotocol.io/)
can call it directly from chat.

---

## Quick Start

1. **Install this extension.** VS Code registers the `veo` MCP server automatically.
2. **Get an API token** from [Ace Data Cloud](https://platform.acedata.cloud) → *API Keys*. New accounts include free trial credit.
3. **Open Copilot Chat** in agent mode and ask for a video task — VS Code will prompt for the token the first time and store it securely.

> The default config talks to the **hosted streamable-HTTP endpoint** at
> `https://veo.mcp.acedata.cloud/mcp` — no Python, no `uvx`, no local install needed.

### Example prompts

- "Generate a Veo 3.1 video of a vintage train pulling into a snowy station at dusk."
- "Animate https://example.com/portrait.jpg into a Veo Fast clip with subtle head turn."

---

## Tool Reference

**8 tools** available via this server.

| Tool | Description |
| --- | --- |
| `veo_text_to_video` | Generate AI video from a text prompt using Veo. |
| `veo_image_to_video` | Generate AI video from one or more reference images using Veo. |
| `veo_get_1080p` | Get the 1080p high-resolution version of a generated video. |
| `veo_get_task` | Query the status and result of a video generation task. |
| `veo_get_tasks_batch` | Query multiple video generation tasks at once. |
| `veo_list_models` | List all available Veo models and their capabilities. |
| `veo_list_actions` | List all available Veo API actions and corresponding tools. |
| `veo_get_prompt_guide` | Get guidance on writing effective prompts for Veo video generation. |

## Supported Models

`veo-2`, `veo-2-fast`, `veo-3`, `veo-3-fast`, `veo-3.1`, `veo-3.1-fast`

## Pricing

From $0.30 per clip. Free trial credit on sign-up. See full pricing at [https://docs.acedata.cloud](https://docs.acedata.cloud).

---

## Configuration

This extension contributes the following entry to your VS Code MCP config:

```jsonc
{
  "servers": {
    "veo": {
      "type": "http",
      "url": "https://veo.mcp.acedata.cloud/mcp",
      "headers": { "Authorization": "Bearer ${input:acedatacloud_api_token}" }
    }
  },
  "inputs": [
    {
      "type": "promptString",
      "id": "acedatacloud_api_token",
      "description": "Ace Data Cloud API token",
      "password": true
    }
  ]
}
```

VS Code will prompt for the token on first use and persist it in the OS
secret store (Keychain / Credential Manager / libsecret).

### Alternative: local stdio (no network roundtrip)

If you prefer running the server locally — for offline dev, air-gapped
environments, or to pin to a specific PyPI version — install
[`uv`](https://docs.astral.sh/uv/) and replace your `mcp.json` entry with:

```jsonc
{
  "servers": {
    "veo": {
      "type": "stdio",
      "command": "uvx",
      "args": ["mcp-veo"],
      "env": { "ACEDATACLOUD_API_TOKEN": "${input:acedatacloud_api_token}" }
    }
  }
}
```

`uvx` will download and run the latest [`mcp-veo`](https://pypi.org/project/mcp-veo/) on demand.

### Alternative: OAuth via Dynamic Client Registration

The hosted endpoint also accepts OAuth 2.1 with [DCR](https://datatracker.ietf.org/doc/html/rfc7591).
Drop the `headers` and `inputs` blocks and VS Code will run the auth flow on
first use (redirect URL `http://127.0.0.1:33418` or `https://vscode.dev/redirect`).

---

## Links

- **Hosted endpoint:** https://veo.mcp.acedata.cloud/mcp
- **PyPI package:** [`mcp-veo`](https://pypi.org/project/mcp-veo/)
- **Source repository:** https://github.com/AceDataCloud/VeoMCP
- **Ace Data Cloud platform:** https://platform.acedata.cloud
- **MCP documentation:** https://docs.acedata.cloud

## License

MIT — see [LICENSE](LICENSE).

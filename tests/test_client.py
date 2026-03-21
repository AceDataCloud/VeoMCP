"""Unit tests for async submission behavior in the HTTP client."""

from core.client import VeoClient


def test_with_async_callback_injects_default_callback() -> None:
    """Long-running Veo operations should default to async submission."""
    client = VeoClient(api_token="test-token", base_url="https://api.test.com")
    payload = client._with_async_callback({"action": "text2video"})
    assert payload["callback_url"] == "https://api.acedata.cloud/health"


def test_with_async_callback_preserves_explicit_callback() -> None:
    """User-provided callbacks should not be overwritten."""
    client = VeoClient(api_token="test-token", base_url="https://api.test.com")
    payload = client._with_async_callback(
        {"action": "text2video", "callback_url": "https://example.com/webhook"}
    )
    assert payload["callback_url"] == "https://example.com/webhook"

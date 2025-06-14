A lightweight mod inspired by Disable Portal Checks.

Since Minecraft 1.19.3, the game adds a collision check when an entity travels through a portal to ensure it doesn't spawn inside blocks. If a collision is detected, it attempts to find a nearby "free" position using findCollisionFreePosition().

While this makes sense for players, it introduces unnecessary overhead for non-living entities like dropped items, experience orbs, or arrows — especially in high-traffic portal setups (e.g., mob farms or automation).

This mod tweaks that logic via Mixin. By default, it skips the collision check for non-living entities, while still preserving it for players and mobs.

You can configure it further:

🧍 Enable the check only for players
💨 Disable the check for all entities completely
⚙️ All behavior is configurable via a simple config file —— config/PortalCheckTweaks.toml

Ideal for improving some performance in Nether farms or custom modded portals that use the same method.

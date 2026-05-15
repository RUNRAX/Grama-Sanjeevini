# Graph Report - .  (2026-05-07)

## Corpus Check
- 14 files · ~3,142 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 43 nodes · 29 edges · 14 communities detected
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 13|Community 13]]

## God Nodes (most connected - your core abstractions)
1. `MainActivity` - 5 edges
2. `ReflowFragment` - 3 edges
3. `SettingsFragment` - 3 edges
4. `SlideshowFragment` - 3 edges
5. `TransformFragment` - 3 edges
6. `TransformAdapter` - 3 edges
7. `ExampleInstrumentedTest` - 2 edges
8. `ExampleUnitTest` - 2 edges
9. `ReflowViewModel` - 1 edges
10. `SettingsViewModel` - 1 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Communities

### Community 0 - "Community 0"
Cohesion: 0.25
Nodes (3): TransformAdapter, TransformFragment, TransformViewHolder

### Community 1 - "Community 1"
Cohesion: 0.33
Nodes (1): MainActivity

### Community 2 - "Community 2"
Cohesion: 0.5
Nodes (1): ReflowFragment

### Community 3 - "Community 3"
Cohesion: 0.5
Nodes (1): SettingsFragment

### Community 4 - "Community 4"
Cohesion: 0.5
Nodes (1): SlideshowFragment

### Community 5 - "Community 5"
Cohesion: 0.67
Nodes (1): ExampleInstrumentedTest

### Community 6 - "Community 6"
Cohesion: 0.67
Nodes (1): ExampleUnitTest

### Community 7 - "Community 7"
Cohesion: 1.0
Nodes (1): ReflowViewModel

### Community 8 - "Community 8"
Cohesion: 1.0
Nodes (1): SettingsViewModel

### Community 9 - "Community 9"
Cohesion: 1.0
Nodes (1): SlideshowViewModel

### Community 10 - "Community 10"
Cohesion: 1.0
Nodes (1): TransformViewModel

### Community 11 - "Community 11"
Cohesion: 1.0
Nodes (0): 

### Community 12 - "Community 12"
Cohesion: 1.0
Nodes (0): 

### Community 13 - "Community 13"
Cohesion: 1.0
Nodes (0): 

## Knowledge Gaps
- **5 isolated node(s):** `ReflowViewModel`, `SettingsViewModel`, `SlideshowViewModel`, `TransformViewHolder`, `TransformViewModel`
  These have ≤1 connection - possible missing edges or undocumented components.
- **Thin community `Community 7`** (2 nodes): `ReflowViewModel.kt`, `ReflowViewModel`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 8`** (2 nodes): `SettingsViewModel.kt`, `SettingsViewModel`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 9`** (2 nodes): `SlideshowViewModel.kt`, `SlideshowViewModel`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 10`** (2 nodes): `TransformViewModel.kt`, `TransformViewModel`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 11`** (1 nodes): `build.gradle.kts`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 12`** (1 nodes): `settings.gradle.kts`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 13`** (1 nodes): `build.gradle.kts`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What connects `ReflowViewModel`, `SettingsViewModel`, `SlideshowViewModel` to the rest of the system?**
  _5 weakly-connected nodes found - possible documentation gaps or missing edges._
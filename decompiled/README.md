# Clayium 0.4.6.36.hotfix2 Decompile Notes

Input:
- `clayium-0.4.6.36.hotfix2.jar`
- SHA256: `C16E35D241D46C290E07F5D0CFD35E498DB79BE33A374615782163EF0F5801F2`
- Minecraft: `1.7.10`
- Mod version: `0.4.6.36.hotfix2`

Tool:
- CFR `0.152`
- Local jar: `tools/cfr-0.152.jar`

Command:

```powershell
java -jar tools\cfr-0.152.jar clayium-0.4.6.36.hotfix2.jar --outputdir decompiled\clayium-0.4.6.36.hotfix2-cfr --caseinsensitivefs true
```

Outputs:
- Java sources: `decompiled/clayium-0.4.6.36.hotfix2-cfr`
- Extracted non-class resources: `decompiled/clayium-0.4.6.36.hotfix2-resources`

Counts:
- `.class` files in jar: 439
- `.java` files emitted by CFR: 356
- Extracted non-class resources: 420

Note: The Java source count is lower than the class count because CFR folds many inner classes into their outer `.java` files.

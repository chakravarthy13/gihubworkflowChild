# GitHub Workflows Setup Guide

## Summary
Your submodule is now set up with automatic update workflows. Here's what was created:

### Files Created in Child Repo:

1. **.gitmodules** - Defines the submodule reference to the main repo
2. **.github/workflows/update-submodule.yml** - Pulls latest updates from main repo
3. **.github/workflows/notify-child-on-push.yml** - Listens for push events (runs in main repo)

## Setup Instructions for Complete Automation:

### Step 1: Add Personal Access Token (PAT)
1. Go to https://github.com/settings/tokens
2. Click "Generate new token" → "Generate new token (classic)"
3. Give it access: `repo` (full control of private repositories)
4. Copy the token

### Step 2: Add Secret to Child Repo
1. Go to child repo: https://github.com/chakravarthy13/gihubworkflowChild
2. Settings → Secrets and variables → Actions
3. Click "New repository secret"
4. Name: `REPO_ACCESS_TOKEN`
5. Value: Paste your PAT

### Step 3: Add Workflow to Main Repo
1. Clone/navigate to main repo locally
2. Create `.github/workflows/` directory
3. Add the following workflow file to trigger child repo updates:

**File: .github/workflows/trigger-child-update.yml**
```yaml
name: Trigger Child Repo Update

on:
  push:
    branches:
      - main
      - master

jobs:
  trigger-update:
    runs-on: ubuntu-latest
    
    steps:
      - name: Trigger child repository workflow
        run: |
          curl -X POST \
            https://api.github.com/repos/chakravarthy13/gihubworkflowChild/dispatches \
            -H "Accept: application/vnd.github.everest-preview+json" \
            -H "Authorization: token ${{ secrets.CHILD_REPO_ACCESS_TOKEN }}" \
            -d '{"event_type":"main-repo-updated"}'
```

4. Add `CHILD_REPO_ACCESS_TOKEN` secret to main repo (same PAT as child repo)

## How It Works:

1. **You push code to Main Repo** ↓
2. **Workflow in Main Repo triggers** ↓
3. **Sends notification to Child Repo** ↓
4. **Child Repo workflow updates the submodule** ↓
5. **Latest code from Main is now in Child Repo**

## Test It:

1. Make a change in main repo and push
2. Go to Child Repo → Actions tab
3. You should see the "Update Submodule" workflow running
4. Check if submodule commit was updated in child repo

## Additional Notes:

- The `update-submodule.yml` also runs on a daily schedule (midnight UTC)
- Manual trigger available: Go to Actions → Update Submodule → Run workflow
- The workflow will only commit if there are actual changes

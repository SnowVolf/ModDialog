# Mod Dialog
Custom dialog for showing in modified Android apps
API 21+
## Supported functions

1. Custom background color
2.  Custom avatar 
3. Changing the text color
4. Completely in code, no generation of layout resources is required
5. The ability to display only at the first launch
6.  The ability to use HTML formatting (hyperlinks are supported). See `/assets/about_cracker.txt`

| Assets file name | Description | Option |
|--|--|--|
| av.webp | Avatar image (`100x100` or `200x200 px`) | required |
| bg.webp | Background image `800x400` px (landscape) | required|
| about_cracker.txt | Dialog message text | required |
| relish.ttf | Custom font | optional (if font type variable set to CUSTOM) |


## Screenshot 
![Imgur link](https://i.imgur.com/Cs985ki.png)
## Integration 
- Copy the files from the `/assets/` folder
- Copy the files from the smail folder while preserving the package structure (`CircleImageView` and `ModDialog`)
- Use the `prepare()` method of the ModDialog class to show the dialog. See the source code for details
---
- Only first launch

    ModDialog.prepare(this);

- Always show

    ModDialog.showCrackerDialog(this);

---

> Written with [StackEdit](https://stackedit.io/).


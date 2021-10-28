# Bright Pattern Flickr test app

## Evaluating skills
1) Android dependency usage
2) MVVM design pattern
3) Android Jetpack usage
4) Working with external API
5) Following the design inputs
6) Following Android UI and developing guidelines
7) Kotlin coroutines
8) Coding style and testability

## Development times not more than 16 hours

## Environment:
- Android Studio (actual version)
- Kotlin (actual version)
- API 21+

## Description:
Create an Android app with 1 activity and 2 fragments to show a list of Flikr’s photos, continuously and filtered by the searching keyword if it exists. https://www.flickr.com/services/api/
The app should be entirely written by yourself; the contents of your project will be discussed with you personally on the next hiring stage. Here is a check list down below to help track your progress for this technical task implementation. Try to achieve as many points as you can, it will surely be advantageous. Try to avoid using third party libraries as much as possible.

## Guidelines General:
1. Create a project on GitHub and share a link with us when the implementation is ready for a demo
2. Show the best of working with git: branching, committing, commenting, etc.
3. While designing elements on the screen, stick to the 8-point grid system:
   https://builttoadapt.io/intro-to-the-8-point-grid-system-d2573cde8632
4. Follow MVVM pattern and Android app architecture’s guideline
5. Write comments and leave marks
6. Variables should have sensible names
7. No compiler errors: the app should be successfully compiled
8. No runtime crashes

## Screen Content:
1. Use a 20 items page, “pull-2-reload” and auto download content if the end of the list is reached and the search result has more items.
2. The user should be able to interact with app while it’s fetching the photos
3. Tapping on the item will follow the user to the details screen
4. The user can save the image on the detail screen by long tap on it.
5. Create and use the Navigation Resource file
6. Avoid to hardcoding texts use Constants for numbers
7. (Optional) add unit tests and mock data
8. (Optional) while image loading display loading indicator
9. (Optional) Cache downloaded images, so a user, after returning from Details Screen, will
   see all cells populated with images

## What didn't done:
1. unit tests
2. while image loading display loading indicator, but common loading indicator was implemented.

## Mock data
Select one of the Build Variant to use mocked or prod implementation of repository.

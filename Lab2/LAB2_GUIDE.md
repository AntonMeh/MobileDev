# Підготовка до захисту Лабораторної роботи №2
**Тема:** Організація навігації у Jetpack Compose (**Jetpack Navigation 3**)

---

## 1. Теоретична частина (Що запитає викладач)

### 1.1. Які існують способи організації навігації в Jetpack Compose? (Пункт 0)
1. **Через простий стан (State-based / умовний рендеринг):**
   - Зберігається поточна вкладка у змінній `var currentDestination by rememberSaveable { mutableStateOf(...) }` і перемикається через `when (currentDestination)`.
   - **Де використовується у нас:** у верхньорівневому меню `NavigationSuiteScaffold` у `MainActivity.kt` (перемикання між вкладками «Головна», «Лічильник», «Студент»).
2. **Jetpack Navigation 2 (`androidx.navigation:navigation-compose`):**
   - Попереднє покоління навігації, де використовувались `NavHost` та `NavController`, а стек переходів (Back Stack) був «чорною скринькою» всередині `NavController`.
3. **Jetpack Navigation 3 (`androidx.navigation3:navigation3-ui` та `navigation3-runtime`) — ВИКОРИСТОВУЄТЬСЯ У НАС:**
   - Нова бібліотека навігації, спроєктована спеціально для Jetpack Compose.
   - **Головна ідея Navigation 3:** ти сам володієш стеком навігації (`backStack`) як звичайним реактивним списком станів (`mutableStateListOf`), а компонент **`NavDisplay`** автоматично відображає верхній елемент цього списку.

---

### 1.2. Головні компоненти Jetpack Navigation 3
Викладач обов'язково запитає: *«З яких частин складається Navigation 3 і чим він кращий за Navigation 2?»*

1. **Ключі маршрутів (`MainSubRoute`):**
   - Описуються як `sealed interface` з об'єктами `data object SubScreen1`, `SubScreen2`, `SubScreen3` (або `data class`, якщо треба передати аргументи). Це дає 100% типобезпеку (Type Safety) без рядкових маршрутів.
2. **Власний стек переходів (`backStack`):**
   - Створюється як реактивний список:
     ```kotlin
     val backStack = remember { mutableStateListOf<MainSubRoute>(MainSubRoute.SubScreen1) }
     ```
   - **Перехід вперед:** просто додаємо елемент у кінець списку — `backStack.add(MainSubRoute.SubScreen2)`.
   - **Перехід назад:** просто видаляємо останній елемент зі списку — `backStack.removeLastOrNull()`.
3. **`NavDisplay`:**
   - Головний `@Composable` контейнер у Navigation 3 (заміна старого `NavHost`).
   - Спостерігає за `backStack`, малює поточний (останній у списку) екран, відповідає за анімації переходів і автоматично перехоплює системну кнопку / жест «Назад» через параметр `onBack`.
4. **`entryProvider` та `entry<T>`:**
   - Функція-білдер, яка зіставляє кожен ключ маршруту (`MainSubRoute.SubScreen1`) із відповідною `@Composable` функцією (`NavEntry`).

---

## 2. Пояснення коду проєкту `Lab2`

### 2.1. Структура проєкту (за зразком викладача)
- `MainActivity.kt` — точка входу в додаток та глобальна навігація по вкладках (`NavigationSuiteScaffold` + `AppDestinations`).
- `AppViewModel.kt` — `ViewModel`, яка зберігає дані додатку у `StateFlow` (`message`, `count`, `studentInfo`).
- `ui/screens/MainScreen.kt` — головний файл для **Лабораторної №2**, де реалізовано **Navigation 3** між трьома підекранами.
- `ui/screens/CounterScreen.kt` та `ui/screens/StudentScreen.kt` — інші вкладки з першої лабораторної роботи.

---

### 2.2. Розбір файлу `ui/screens/MainScreen.kt`

```kotlin
// 1. Оголошуємо ключі підекранів
sealed interface MainSubRoute {
    data object SubScreen1 : MainSubRoute
    data object SubScreen2 : MainSubRoute
    data object SubScreen3 : MainSubRoute
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    // 2. Створюємо стек переходів із початковим екраном SubScreen1
    val backStack = remember { mutableStateListOf<MainSubRoute>(MainSubRoute.SubScreen1) }

    // 3. Відображаємо поточний підекран через NavDisplay
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider {
            entry<MainSubRoute.SubScreen1> {
                MainSubScreen1(
                    appViewModel = appViewModel,
                    onNavigateToSecond = { backStack.add(MainSubRoute.SubScreen2) }
                )
            }
            entry<MainSubRoute.SubScreen2> {
                MainSubScreen2(
                    onNavigateToThird = { backStack.add(MainSubRoute.SubScreen3) },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<MainSubRoute.SubScreen3> {
                MainSubScreen3(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
```

---

## 3. Типові запитання викладача на захисті та відповіді

1. **Питання:** *«У чому головна різниця між Navigation 2 та Navigation 3?»*
   - **Відповідь:** У Navigation 2 стек переходів був закритий всередині `NavController` (ми не могли напряму читати чи редагувати список екранів як звичайний Compose State). У **Navigation 3** стек (`backStack`) належить розробнику — це звичайний реактивний список `SnapshotStateList` (`mutableStateListOf`), де перехід уперед — це `backStack.add()`, а повернення назад — `backStack.removeLastOrNull()`. А компонент `NavDisplay` просто відображає цей список.

2. **Питання:** *«Як у Navigation 3 працює системна кнопка або жест "Назад"?»*
   - **Відповідь:** Коли у `backStack` більше одного елемента, `NavDisplay` автоматично перехоплює системний жест «Назад» і викликає колбек `onBack = { backStack.removeLastOrNull() }`, який видаляє верхній підекран зі стеку і повертає користувача на попередній підекран.

3. **Питання:** *«Чому коли ми змінюємо текст на першому підекрані, переходимо на другий підекран і повертаємось назад — текст не скидається?»*
   - **Відповідь:** Тому що текст зберігається в `AppViewModel` у потоці `StateFlow`, який живе на рівні всього додатку і не знищується при перемиканні записів (`NavEntry`) у `NavDisplay`.

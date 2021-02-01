import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

fun main(args: Array<String>) {

    exampleOf("PublishSubject") {
        val quotes = PublishSubject.create<String>()
        quotes.onNext(itsNotMyFault)

        val subscriptionOne = quotes.subscribeBy(
            onNext = { printWithLabel("1)", it) },
            onComplete = { printWithLabel("1)", "Complete") }
        )

        quotes.onNext(doOrDoNot)

        val subscriptionTwo = quotes.subscribeBy(
            onNext = { printWithLabel("2)", it) },
            onComplete = { printWithLabel("2)", "Complete") }
        )

        quotes.onNext(lackOfFaith)
        subscriptionOne.dispose()
        quotes.onNext(eyesCanDeceive)

        quotes.onComplete()

        val subscriptionThree = quotes.subscribeBy(
            onNext = { printWithLabel("3)", it) },
            onComplete = { printWithLabel("3)", "Complete") }
        )

        quotes.onNext(stayOnTarget)

        subscriptionTwo.dispose()
        subscriptionThree.dispose()

    }

    exampleOf("BehaviorSubject"){
        val subscriptions = CompositeDisposable()
        val quotes = BehaviorSubject.createDefault(iAmYourFather)

        val subscriptionOne = quotes.subscribeBy(
            onNext = { printWithLabel("1)", it) },
            onError = { printWithLabel("1)", it) },
            onComplete = { printWithLabel("1)", "Complete") }
        )

        quotes.onError(Quote.NeverSaidThat())

        subscriptions.add(quotes.subscribeBy(
            onNext = { printWithLabel("2)", it) },
            onError = { printWithLabel("2)", it) },
            onComplete = { printWithLabel("2)", "Complete") }
        ))

        subscriptionOne.dispose()
        subscriptions.dispose()
    }

    exampleOf("BehaviorSubject status"){
        val subscriptions = CompositeDisposable()
        val quotes = BehaviorSubject.createDefault(mayTheForceBeWithYou)

        println(quotes.value)

        subscriptions.add(quotes.subscribeBy {
            printWithLabel("1)", it)
        })

        quotes.onNext(mayThe4thBeWithYou)

        println(quotes.value)
        subscriptions.dispose()
    }
}
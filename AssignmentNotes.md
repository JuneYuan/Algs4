# Assignment Notes

### Week1 Percolation

思路
+ `Percolation.java`

 1. n\*n的grid，加上顶部和底部两个虚拟节点，需要一个size为(n\*n + 2)的并查集`uf`.
 2. 上课讲`UF`数据结构时使用的TestClient，每次都给出一个数对pair(p, q)，就直接进行一次`uf.union()`操作；而这里则是，每次指定一个site要求`open()`它，open操作会触发`uf.union()`操作——即“动态连通性”问题，每打开一个格子，都可能改变整个grid的连通性，所以要及时更新`uf`对象的信息（然而一开始竟然把`uf.union()`放在了构造函数中，还良久没看出问题……）
 3. 明确了构造函数`Percolation()`、`open()`怎么做，其余几个函数就都是一两行代码了，当然还要加上异常处理。
 4. 还需(cai)要(guo)注(le)意(keng)的是，每一处二位下标和一维下标的正确填写。
 
另外在打印置信区间时，用的printf("95%...", ...)，由于`%`没有转义，导致`java.util.FormatFlagsConversionMismatchException`，也找了很久原因。
 
+ `PercolationStats.java`

 这个类的职责就是计算mu, sigma, 和置信区间。Specification中已经给出了计算公式，显而易见，这三个值计算都是依赖于样本值的得出，也就是`x[]`数组。而`x[]`数组的每个元素，都来自一次模拟试验，实验过程为：反复open随机的一个site，直至整个grid能够`percolates()`，这时统计共open了多少个site、占site总数比例为多少，即可。
 
### Week2 Deques and Randomized Queues

+ `Randomized Queues`写的很顺利。

+ 主要问题出在`Deques`中双链表的操作上。
 1. 操作过程中没有妥善处理`last`指针。
 1. 还有使用了头节点却没写对相应的`addFirst()`和`removeFirst()`。这里的`addFirst()`和`addLast()`操作并不对称，因为使用了头节点，所以`addFirst()`其实并非“头插”，而是插入在`dummy`的下一位置；`addLast()`则直接就是“尾插”。`removeFirst()`同理，不是删除“头删”（删除头节点`first`），而是删`first`的下一位置。 
 1. 此外还出现了空间复杂度过高、内存泄漏问题。
 
### Week 3 Collinear Points / Pattern Recognition
 
#### 一、思路

+ BruteCollinearPoints (Easy)

> 
Interate through all combinations of 4 points (N choose 4) and check whether they all lie on the same line segment. To check whether the 4 points `p, q, r, s` are all collinear, check whether the three slopes between `p and q`, between `p and r`, and between `p and s` are all equal.

Complexity: n^4

+ FastCollinearPoints (Medium)

> 
A faster, sorting-based solution. **Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.**
> 
 1. Think of p as the origin.
 1. For each other point q, determin the slope it makes with p.
 1. Sort the points according to the slopes they make with p.
 1. Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.  
> 
Applying this method for each of the n points in turn yield to an efficient algorithm to the problem. **The algorithm solves the problem because point that have equal slopes with respect to p are collinear, and sorting brings such points together.** The algorithm is fast because the bottleneck operation is sorting.
 
#### 二、Trials & Errors

Q1. 对`points[]`的排序效果不正确：1. 斜率都是0的点被分开了；2. 其余的点顺序也有不对。

A1. “1”是因为根据`∆y / ∆x`计算斜率，当`∆y = 0`时，视`∆x`的正负，结果会呈`+0.0`或`-0.0`；“2”是因为比较器的`compare()`函数有错误。

![](http://ww3.sinaimg.cn/mw690/6b9392ddgw1f93l23gxu2j20lo06mwg6.jpg)

Q2. 起点、终点保持不变，仅中间点变化了，是否仍判定为不同的line？

Q3. `helper(p)`要按照个点相对于`p`的斜率进行排序，那么是不是要用一个`double[]`数组来记录下每个斜率值然后挨个比较呢？

A3. 并不用，而且这是不可取的。试想求得了`double[]`数组中的连续等值区间段后，如何知道这都是哪些点对应的斜率呢？定义一个`Pair`内部类吧？包含`double slope; Point p`两个属性，然后把`double[]`数组改为`Pair[]`数组？也不是不行，但完全没有必要。

`Comparator`的作用就是**自定义规则**比较，比较的对象没有发生变化（比如这里，比较对象就是`Point[] points`而不应该中途变成一个`double[] slope`什么的），只是可以对比较规则作各种设定。

Q4. `FastCollinearPoints`中执行`for (Point p : points) { helper(p); }`，而每次`helper()`都会对`points`按照`p.slopeOrder()`重新排序，影响`points`迭代器的后续行为。所以`helper()`应该先对`points`进行深拷贝，再排序。

Q5. 如何避免重复，比如一条线段上的每个点都可以检测到这条线，如何保证该线段只输出一次？

A5. 办法不唯一。可以全部查找并加入结果，然后去重；也可以在检测过程中直接过滤。

过滤条件也不唯一，比如`helper(p)`执行过程中找到了`3 (or more) adjacent points having equal slopes with respect to p`, 而这些点中有按natual order比`p`更小的点，就舍弃这一组点对应的线段，因为`p`并非这条线段的起点。

Q6. 程序莫名地卡在某个数据点的处理过程中，陷入死循环。

A6. 更明确的原因实际是：`check any 3 (or more) adjacent points having equal slopes with respect to p`的判断条件有误：写成`while (deltaSlope < 10^-5)`对于一般的斜率值是没有问题的，但对于线段平行于y轴（即`slope == +∞`）的情况就失效了，因为这时`deltaSlope = Double.POSITIVE_INFINITE - Double.POSITIVE_INFINITE`，这不是个数，Java中用`Double.NaN`来表示，它不能参与数值比较运算。

![](http://ww3.sinaimg.cn/mw690/6b9392ddgw1f93pnjppfzj205y02eaa4.jpg)

Q7. 如何让程序在某一条数据上触发断电进行调试

A7. 

![](http://ww3.sinaimg.cn/mw690/6b9392ddgw1f93pp58g1wj20e302it95.jpg)

#### BTW

Q8. LeetCode P151中学到的处理一次遍历过程中较复杂的中间情况。这里比P151更复杂一些，还要筛选过滤特定的区间段，什么时候break；什么时候设置flag, flag设在那里，如何初始化值，等等。都需要仔细考虑否则就得花大量时间调试。这个过程，相比于最后submit的代码，也是极为“非线性”的。

Q9. 学习使用`assert`, Junit也是不错办法。当然最好还是动手写代码前想得尽可能明白。

Q10. 
> 
Comparable interface: sort using a type's natural order.  
Comparator interface: sort using an alternate order.

### Week 4: 8 Puzzle
 
#### 一、思路

+ Solver

> 
**Best-first search.** A solution to the problem is a general artificial intelligence methodology known as the **A* search algorithm**. We define a *search node* of the game tobe a board, the number of moves made to reach the board, and the previous search node. First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue. Then delete from the priority queue the search node with the minimum priority, and insert onto the priority queue non-duplicate neighboring search nodes (those that canbe reached in one move from the dequeued search node and that have not enqueued before). Repeat this procedure until the search node dequeued corresponds to a goal board.
The success of this approach hinges on the chioce of *priority function* for a search node. Two priority functions are considered: 
> 
+ *Hamming priority function*. The number of blocks in the wrong position, plus the number of moves made so far to get to the search node.
+ *Manhattan priority function*. The sum of the Manhattan distances (sum of vertical and horizontal distance) from the blocks to their goal positions, plus the number of moves made so far to get to the search node.

> 
![](http://ww4.sinaimg.cn/mw690/6b9392ddgw1f9cg094n0lj20mo04hwfo.jpg)
>
**Detecting unsolvable puzzles.** Not all initial boards can lead to the goal board by a sequence of legal moves. There is a fact that boards are divided into two equivalence classes with respect to reachability: (i) those that lead to the goal board, and (ii) those that lead to the goal board if we modify the initial board by swapping any pair of blocks (the blank square is not a blank). (*Proof needed.*) To apply the fact, run the A* algorithm on *two* puzzle instances -- one with the initial board and the other with the initial board modified by swapping a pair of blocks -- in lockstep ( alternating back and forth between exploring search nodes in each of the two game trees). Exactly one of the two will lead to the goal board.

既然 `Solver` 类的职责是求解 8 puzzle 游戏的 solution, 那么求解的过程自然就放在构造函数中了，由构造函数完成计算过程，其余的 API 都只需要“展示”一下相应的结果就好了。

+ Board

这是为求解 8 puzzle 问题而单独定义出来的一个类。关键就是记录几个重要的值（数据结构），便于实现 API 中约定的方法。这些关键的值有：1. 维数 `n`. 几乎每个方法都用到。2. 对传入的 `block[][]` 作深拷贝，因为题目要求实现 `immutable data type`. 3. 此外还应记录 `blank square` 对应的行、列下标。主要有两个地方要用到，一是计算 `neighbors()` 时，需要将 blank 与其左邻右舍交换值，二是计算 `twin()` 时，需要保证进行 swap 操作的一对 blocks 当中没有包括 blank. 
 
#### 二、Trials & Errors

Q1. `Board` 类中原先写了一个 `private int[][] goal`, 这是多余的，并且导致 memory 相关的 case 全都 fail. 其实这个 `goal[][]` 里面的每个值都可以根据当前的下标 `i`, `j` 直接计算出来。

Q2. `Board.twin()` 的计算有误：没有注意到，进行交换的block不能是那个 `blank`.

Q3. `neighbors()` 的计算有误：

错误的：

```
// all neighboring boards
public Iterable<Board> neighbors() {
	Queue<Board> result = new Queue<>();
	
	int[][] directions = new int[][]{
		{-1, 0}, {1, 0}, {0, -1}, {0, 1}
	};
	for (int[] direc : directions) {
		Board board = new Board(this);
		boolean success = swap(board.copy, blankX, blankY, blankX + direc[0], blankY + direc[1]);
		if (success) {
			result.enqueue(board);
		}
	}
	
	return result;
}
```

正确的：

```
boolean success = swap(board.copy, board.blankX, board.blankY, board.blankX + direc[0], board.blankY + direc[1]);
```

Q4. 关键字 `final`, `static` 的使用规则。

Q5. `class Solver` 中的 `private Node goalNode` 初始化错误，导致最小步数为 0 的 case (即 initial 本身就是 goal)，答案错误。

A5. `goalNode` 的声明：

```
private Node goalNode;       // the goal Node, used to backtrace the solution sequence
```

错误的初始化：

```
goalNode = new Node(null, 0, null);
```

正确的初始化：

```
goalNode = new Node(initial, 0, null);
```

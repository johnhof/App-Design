/*
 * GameScene.cpp
 *
 *  Created on: Mar 1, 2013
 *      Author: john
 */

#include "/home/john/android/cocos2d-2.0-x-2.0.4/project3/Classes/GameScene.h"
#include "SimpleAudioEngine.h"
#include "HomeScene.h"
#include "OptionScene.h"

using namespace cocos2d;
using namespace CocosDenshion;

#define BLOCK 1
#define BALL 2
#define PADDLE 3
#define HBORDER 4
#define VBORDER 5

#define BORDER_SIZE 5

#define BALLLSPEED 7
#define PADDLESPEED 400

#define BOTTOM 0
#define TOP 1
#define LEFT 0
#define RIGHT 1

GameScene::GameScene() :_blocks(NULL), _hBorders(NULL), _vBorders(NULL)
{
    ball = NULL;
    paddle = NULL;
    label = NULL;
}

GameScene::~GameScene()
{
	if (_blocks)
	{
		_blocks->release();
		_blocks = NULL;
	}

	if (_hBorders)
	{
		_hBorders->release();
		_hBorders = NULL;
	}

	if (_vBorders)
	{
		_vBorders->release();
		_vBorders = NULL;
	}
}

bool GameScene::init()
{
	if (!CCLayer::init())
		return false;

	_blocks = new CCArray;
	_hBorders = new CCArray;
	_vBorders = new CCArray;

	score = 0;
	ended = 0;

	xDir = 0.9;
	yDir = 1;

	move = 0;

	//get window size
	CCSize winSize = CCDirector::sharedDirector()->getWinSize();

	//get sound state
	soundOn = CCUserDefault::sharedUserDefault()->getBoolForKey("soundOn");

	//----------------------------------------------------------------------------------------------------------------------------------------------------
	//--add ball and paddle
	//----------------------------winSize.width------------------------------------------------------------------------------------------------------------------------

	//create author, set scale, set position
		paddle = CCSprite::create("paddle.png");
		paddle->setTag(PADDLE);
		paddle->setScaleX(winSize.width / (paddle->getContentSize().width * 7));
		paddle->setScaleY(winSize.height / (paddle->getContentSize().height * 40));
		paddle->setPosition(ccp(winSize.width / 2, paddle->getContentSize().height/2));
		this->addChild(paddle);

		//create author, set scale, set position
		ball = CCSprite::create("ball.png");
		ball->setTag(BALL);
		ball->setScaleX(winSize.width / (ball->getContentSize().width * 50));
		ball->setScaleY(winSize.height / (ball->getContentSize().height * 30));
		ball->setPosition(ccp(winSize.width / 2, winSize.height / 2));
		this->addChild(ball);


//----------------------------------------------------------------------------------------------------------------------------------------------------
//--add blocks
//----------------------------------------------------------------------------------------------------------------------------------------------------

	//initial offset for buttons and score
	CCSprite* block = CCSprite::create("block.png");
	float blockHeight = block->getContentSize().height;
	float blockWidth =block->getContentSize().width;
	float winXSeg = winSize.width/6;
	float winYSeg = winSize.height/15;

	//begin incrementation early to create an offset
	for (int i = 2; i <= 3; i++)
	{
		for (int j = 1; j <= 5; j++)
		{
			block = CCSprite::create("block.png");
			block->setTag(BLOCK);
			block->setScaleX(winXSeg/blockWidth);
			block->setScaleY(winYSeg/blockHeight);
			block->setPosition(ccp(winXSeg*j,(winSize.height- winYSeg*i)));
			_blocks->addObject(block);
			this->addChild(block);
		}
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------
//--add borders
//----------------------------------------------------------------------------------------------------------------------------------------------------

	//create two horizontal borders
	//1: bottom
	//2: top
	for (int i = 0; i < 2; i++) {
		CCSprite *hBorder = CCSprite::create("border.png");
		hBorder->setTag(HBORDER);
		hBorder->setScaleX(winSize.width);
		hBorder->setScaleY(BORDER_SIZE);
		hBorder->setPosition(ccp(winSize.width / 2, i * winSize.height));
		_hBorders->addObject(hBorder);
		this->addChild(hBorder);
	}

	//Create two vertical borders
	//1: left
	//2: right
	for (int i = 0; i < 2; i++) {
		CCSprite *vBorder = CCSprite::create("border.png");
		vBorder->setTag(VBORDER);
		vBorder->setScaleX(BORDER_SIZE);
		vBorder->setScaleY(winSize.height);
		vBorder->setPosition(ccp(i * winSize.width, winSize.height / 2));
		_vBorders->addObject(vBorder);
		this->addChild(vBorder);
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------
//--add score
//----------------------------------------------------------------------------------------------------------------------------------------------------

	label = CCLabelTTF::create("","Artial", 32);
	label->retain();
	label->setColor( ccc3(255, 255, 255) );
	char scoreStr[5];
	sprintf(scoreStr,"%d pts", score);
	label->setString(scoreStr);
	label->setPosition(ccp(winSize.width/2, winSize.height-label->getContentSize().height/2));
	this->addChild(label);

//----------------------------------------------------------------------------------------------------------------------------------------------------
//--add ball movement
//----------------------------------------------------------------------------------------------------------------------------------------------------

	if(soundOn)CocosDenshion::SimpleAudioEngine::sharedEngine()->playEffect("fanfare.ogg");
	updateVector();

	this->setTouchEnabled(true);
	this->schedule(schedule_selector(GameScene::update));

	return true;
}

CCScene* GameScene::scene() {
	// 'scene' is an autorelease object
	CCScene *scene = CCScene::create();

	// 'layer' is an autorelease object
	GameScene *layer = GameScene::create();

	// add layer as a child to scene
	scene->addChild(layer);

	// return the scene
	return scene;
}

void GameScene::spriteMoveFinished(CCNode* sender)
{
}

void GameScene::update(float dt)
{
	//we have to wait until after the game ending update to allow GAME OVER to print
	if(ended)endGame();

	stopAllActions();
	float sound = 0;

	CCArray* targetsToDelete = new CCArray;
	CCObject* iBlock = NULL;
	CCObject* iBorder = NULL;

	//get window size
	CCSize winSize = CCDirector::sharedDirector()->getWinSize();

	//create ball and paddle rect
	CCRect ballRect = ball->boundingBox();
	CCRect paddleRect = paddle->boundingBox();

	//check paddle collision
	if(paddleRect.intersectsRect(ballRect))
	{
		//offset and change velocity
		ball->setPosition(ccp(ball->getPosition().x, (ball->getContentSize().height*ball->getScaleY())+(paddle->getContentSize().height*paddle->getScaleY())));
		if(move)
		{
			xDir*=1;
			yDir*=1.2;
		}
		reverseY();

		if(soundOn)CocosDenshion::SimpleAudioEngine::sharedEngine()->playEffect("click.ogg");

		return;
	}

	//check block collision
	CCARRAY_FOREACH(_blocks, iBlock)
	{
		//create the block rect
		CCSprite *block = dynamic_cast<CCSprite*>(iBlock);
		CCRect blockRect = block->boundingBox();
		//if a collision occurred, remove the block, and reverse the ball velocity
		if(blockRect.intersectsRect(ballRect))
		{

			if(soundOn)CocosDenshion::SimpleAudioEngine::sharedEngine()->playEffect("explosion.ogg");

			this->removeChild(block, true);
			_blocks->removeObject(block);

			if(_blocks->count()==0)
			{
				label->setString("YOU WIN!");
				ended = true;
				return;
			}

			//scoring
			score+=50;
			char scoreStr[10];
			sprintf(scoreStr,"%d pts", score);
			label->setString(scoreStr);

			reverseY();
			return;
		}
	}

	//check horizontal border collision
	CCARRAY_FOREACH(_hBorders, iBorder)
	{
		//create the block rect
		CCSprite *border = dynamic_cast<CCSprite*>(iBorder);
		CCRect borderRect = border->boundingBox();

		//if its a bottom intersection
		if(borderRect.intersectsRect(ballRect) && _hBorders->indexOfObject(iBorder) == BOTTOM)
		{
			stopAllActions();
			label->setString("GAME OVER");
			ended = 1;
			return;
		}

		//if its a top intersection
		if(borderRect.intersectsRect(ballRect) && _hBorders->indexOfObject(iBorder) == TOP)
		{
			//offset and change velocity
			ball->setPosition(ccp(ball->getPosition().x, winSize.height-((ball->getContentSize().height*ball->getScaleY()) + BORDER_SIZE)));
			reverseY();

			if(soundOn)CocosDenshion::SimpleAudioEngine::sharedEngine()->playEffect("click.ogg");

			return;
		}
	}

	//check vertical border collision
	CCARRAY_FOREACH(_vBorders, iBorder)
	{
		//create the block rect
		CCSprite *border = dynamic_cast<CCSprite*>(iBorder);
		CCRect borderRect = border->boundingBox();

		//if its a left intersection
		if(borderRect.intersectsRect(ballRect) && _vBorders->indexOfObject(iBorder) == LEFT)
		{
			//offset and change velocity
			ball->setPosition(ccp((ball->getScaleX()*ball->getContentSize().width) + BORDER_SIZE, ball->getPosition().y));
			reverseX();

			if(soundOn)CocosDenshion::SimpleAudioEngine::sharedEngine()->playEffect("click.ogg");

			return;
		}


		//if its a right intersection
		if(borderRect.intersectsRect(ballRect) && _vBorders->indexOfObject(iBorder) == RIGHT)
		{
			//offset and change velocity
			ball->setPosition(ccp( winSize.width-((ball->getScaleX()*ball->getContentSize().width) + BORDER_SIZE),ball->getPosition().y));
			reverseX();

			if(soundOn)CocosDenshion::SimpleAudioEngine::sharedEngine()->playEffect("click.ogg");

			return;
		}
	}
}

void GameScene::updateVector()
{

	//get window size
	CCSize winSize = CCDirector::sharedDirector()->getWinSize();

	//determine where its going
	int realX = winSize.width*2;
	int realY = winSize.height*2;
	CCPoint realDest = ccp(realX*xDir, realY*yDir);

	//determine length of how far we're shooting
	int offRealX = realX - ball->getPosition().x;
	int offRealY = realY - ball->getPosition().y;
	float length = sqrtf((offRealX * offRealX)+(offRealY * offRealY));

	float velocity = 200;
	float realMoveDuration = length/velocity;

	//move to actual endpoint
	ball->runAction(CCSequence::create(CCMoveTo::create(realMoveDuration, realDest),NULL));
}


void GameScene::ccTouchesBegan(cocos2d::CCSet* touches, cocos2d::CCEvent* event)
{
	CCTouch* touch = (CCTouch*)(touches->anyObject());
	CCPoint location = touch->locationInView();
	location = CCDirector::sharedDirector()->convertToGL(location);

	float distance = 0;

	if(location.x > paddle->getPosition().x) distance = location.x-paddle->getPosition().x;

	if(location.x < paddle->getPosition().x) distance = paddle->getPosition().x-location.x;

	CCPoint realDest = ccp(location.x, paddle->getPosition().y);
	CCMoveTo* movement = CCMoveTo::create(distance/PADDLESPEED, realDest);
	paddle->runAction(movement);
	move = 1;
}


void GameScene::ccTouchesEnded(cocos2d::CCSet* touches, cocos2d::CCEvent* event)
{
	move = 0;
	paddle->stopAllActions();
}

void GameScene::endGame()
{
	if(1)SimpleAudioEngine::sharedEngine()->playEffect("fanfare.wav");
	/*
    Context context = CCDirector.sharedDirector().getActivity();
    SoundEngine.sharedEngine().playEffect(context, R.raw.pew_pew_lei);
    */
	//Briefly pause
	clock_t wait = clock()+1500000;
	while(clock() < wait);

    // create a scene. it's an autorelease object
	CCScene *pScene = HomeScene::scene();

    // run
    CCDirector::sharedDirector()->replaceScene(pScene);
}


void GameScene::reverseX()
{
	xDir = xDir*-1;
	updateVector();
}

void GameScene::reverseY()
{
	yDir = yDir*-1;
	updateVector();
}
